package org.test.hexpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.ast.ASTNode;
import org.mvel2.ast.Function;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.util.ASTIterator;
import org.mvel2.util.ASTLinkedList;
import org.mvel2.util.SimpleVariableSpaceModel;
import org.mvel2.util.VariableSpaceCompiler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.test.model.Entry;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

public class HExpEntry {

  private static final ParserContext parserContext;

  private static final Cache<String, Serializable> expressionCache =
      CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(2, TimeUnit.HOURS).build();

  // for check3
  private static final ParserContext parserContext3;
  private static final Cache<String, SimpleVariableSpaceModel> modelCache =
      CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(2, TimeUnit.HOURS).build();

  // for check
  private static final VariableResolverFactory functionFactory = new MapVariableResolverFactory();

  static {
    String entryFunc = readFromFile();
    MVEL.eval(entryFunc, functionFactory);
  }

  static {
    String[] varNames = {"entry", "goodsIndex"};
    parserContext3 = ParserContext.create();
    parserContext3.addIndexedInput(varNames);
    parserContext3.setIndexAllocation(true);
  }
  /** Having a parser context that imports the classes speeds MVEL by up to 60%. */
  static {
    parserContext = new ParserContext();
    parserContext.addImport("MVEL", MVEL.class);
    parserContext.addInput("entry", Entry.class);
  }

  public boolean check(String rule, Entry entry) {
    return check(rule, entry, 0);
  }

  public boolean check2(String rule, Entry entry, int goodsIndex) {

    String entryFunc = readFromFile();

    Serializable s = expressionCache.getIfPresent(rule);
    if (s == null) {
      s = MVEL.compileExpression(entryFunc + rule, parserContext);
      expressionCache.put(rule, s);
    }

    entry.setGoodsIndex(goodsIndex);
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("entry", entry);

    return MVEL.executeExpression(s, vars, boolean.class);
  }

  public boolean check3(String rule, Entry entry, int goodsIndex) {
    final String ruleCachePrefix = "check3_rule:";
    final String modelCachePrefix = "check3_model:";

    Object[] values = {entry, goodsIndex};
    String expr = readFromFile() + rule;

    Serializable indexCompile = expressionCache.getIfPresent(ruleCachePrefix + rule);
    SimpleVariableSpaceModel model = modelCache.getIfPresent(modelCachePrefix + rule);
    if (indexCompile == null || model == null) {
      indexCompile = MVEL.compileExpression(expr, parserContext3);
      model = VariableSpaceCompiler.compile(expr, parserContext3);
      expressionCache.put(ruleCachePrefix + rule, indexCompile);
      modelCache.put(modelCachePrefix + rule, model);
    }

    return (boolean) MVEL.executeExpression(indexCompile, model.createFactory(values));
  }

  public boolean check(String rule, Entry entry, int goodsIndex) {
    final String cachePrefix = "check_rule:";

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);

    Serializable s = expressionCache.getIfPresent(cachePrefix + rule);
    if (s == null) {
      s = MVEL.compileExpression(rule, parserContext);
      expressionCache.put(cachePrefix + rule, s);
    }

    entry.setGoodsIndex(goodsIndex);
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("entry", entry);
    return MVEL.executeExpression(s, vars, myVarFactory, boolean.class);
  }

  /**
   * desc：报关单的全局函数，从文件里读入
   *
   * @return
   */
  private static String readFromFile() {
    final String fileName = "entry.mvel";
    Resource resource = new ClassPathResource(fileName);
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

      // 每次读取文件的缓存
      String temp = null;
      StringBuilder data = new StringBuilder();
      while ((temp = reader.readLine()) != null) {
        data.append(temp);
      }
      return data.toString();
    } catch (IOException e) {
      throw new HExpException("读取文件出错：" + fileName);
    }
  }

  private Map<String, Function> extractAllDeclaredFunctions(CompiledExpression compile) {
    Map<String, Function> allFunctions = new LinkedHashMap<String, Function>();
    ASTIterator instructions = new ASTLinkedList(compile.getFirstNode());

    ASTNode n;
    while (instructions.hasMoreNodes()) {
      if ((n = instructions.nextNode()) instanceof Function) {
        allFunctions.put(n.getName(), (Function) n);
      }
    }

    return allFunctions;
  }
}

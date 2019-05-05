package com.example.demo.exp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.optimizers.OptimizerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import com.example.demo.model.Order;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

public class ExpOrder {
  private static final ParserContext parserContext;

  private static final Cache<String, Serializable> expressionCache =
      CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(2, TimeUnit.HOURS).build();
  private static final VariableResolverFactory functionFactory = new MapVariableResolverFactory();

  static {
    String entryFunc = readFromFile();
    MVEL.eval(entryFunc, functionFactory);

    parserContext = new ParserContext();
    parserContext.addImport("MVEL", MVEL.class);

    parserContext.addImport(
        "weekDay", MVEL.getStaticMethod(ExpUtil.class, "weekDay", new Class[0]));
    parserContext.addImport(
        "mid",
        MVEL.getStaticMethod(
            ExpUtil.class, "mid", new Class[] {String.class, int.class, int.class}));

    parserContext.addInput("order", Order.class);
    parserContext.addInput("goodsIndex", int.class);
  }

  public boolean evalToBoolean(String rule, Order order, int goodsIndex) {
    final String cachePrefix = "check_rule:";

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);

    Serializable s = expressionCache.getIfPresent(cachePrefix + rule);
    if (s == null) {
      s = MVEL.compileExpression(rule, parserContext);
      expressionCache.put(cachePrefix + rule, s);
    }

    Map<String, Object> vars = Maps.newHashMap();
    vars.put("order", order);
    vars.put("goodsIndex", goodsIndex);
    OptimizerFactory.setDefaultOptimizer(OptimizerFactory.SAFE_REFLECTIVE);
    boolean result = MVEL.executeExpression(s, vars, myVarFactory, boolean.class);
    OptimizerFactory.setDefaultOptimizer(OptimizerFactory.DYNAMIC);
    return result;
  }

  public String evalToString(String rule, Order order, int goodsIndex) {
    final String cachePrefix = "check_rule:";

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);

    Serializable s = expressionCache.getIfPresent(cachePrefix + rule);
    if (s == null) {
      s = MVEL.compileExpression(rule, parserContext);
      expressionCache.put(cachePrefix + rule, s);
    }

    Map<String, Object> vars = Maps.newHashMap();
    vars.put("order", order);
    vars.put("goodsIndex", goodsIndex);
    OptimizerFactory.setDefaultOptimizer(OptimizerFactory.SAFE_REFLECTIVE);
    String result = MVEL.executeExpression(s, vars, myVarFactory, String.class);
    OptimizerFactory.setDefaultOptimizer(OptimizerFactory.DYNAMIC);
    return result;
  }

  /**
   * desc：全局函数，从文件里读入
   *
   * @return
   */
  private static String readFromFile() {
    final String fileName = "order.mvel";
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
      throw new RuntimeException("读取文件出错：" + fileName);
    }
  }
}

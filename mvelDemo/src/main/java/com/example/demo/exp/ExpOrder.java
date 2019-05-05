package com.example.demo.exp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.mvel2.DataConversion;
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
        "weekDay", MVEL.getStaticMethod(ExpUtils.class, "weekDay", new Class[0]));
    parserContext.addImport(
        "dateNow", MVEL.getStaticMethod(ExpUtils.class, "dateNow", new Class[0]));
    parserContext.addImport(
        "timeNow", MVEL.getStaticMethod(ExpUtils.class, "timeNow", new Class[0]));
    parserContext.addImport(
        "dateTimeNow", MVEL.getStaticMethod(ExpUtils.class, "dateTimeNow", new Class[0]));
    parserContext.addImport("year", MVEL.getStaticMethod(ExpUtils.class, "year", new Class[0]));
    parserContext.addImport("month", MVEL.getStaticMethod(ExpUtils.class, "month", new Class[0]));
    parserContext.addImport(
        "dayOfMonth", MVEL.getStaticMethod(ExpUtils.class, "dayOfMonth", new Class[0]));
    parserContext.addImport(
        "rand", MVEL.getStaticMethod(ExpUtils.class, "rand", new Class[] {int.class}));
    parserContext.addImport(
        "random", MVEL.getStaticMethod(ExpUtils.class, "random", new Class[] {}));
    parserContext.addImport(
        "mid",
        MVEL.getStaticMethod(
            ExpUtils.class, "mid", new Class[] {String.class, int.class, int.class}));
    parserContext.addImport(
        "bt",
        MVEL.getStaticMethod(ExpUtils.class, "bt", new Class[] {Object.class, Object[].class}));

    parserContext.addImport(
        "left",
        MVEL.getStaticMethod(ExpUtils.class, "left", new Class[] {String.class, int.class}));
    parserContext.addImport(
        "right",
        MVEL.getStaticMethod(ExpUtils.class, "right", new Class[] {String.class, int.class}));
    parserContext.addImport(
        "in",
        MVEL.getStaticMethod(ExpUtils.class, "in", new Class[] {Object.class, Object[].class}));
    parserContext.addImport(
        "nullsToZero",
        MVEL.getStaticMethod(ExpUtils.class, "nullsToZero", new Class[] {Object.class}));

    parserContext.addInput("order", Order.class);
    parserContext.addInput("goodsIndex", int.class);
  }

  public boolean evalToBoolean(String rule, Order order, int goodsIndex) {
    Object result = eval(rule, order, goodsIndex);
    return DataConversion.convert(result, boolean.class);
  }

  public String evalToString(String rule, Order order, int goodsIndex) {
    Object result = eval(rule, order, goodsIndex);
    return DataConversion.convert(result, String.class);
  }

  public Object eval(String rule, Order order, int goodsIndex) {
    final String cachePrefix = "rule:";
    String cacheKey = cachePrefix + rule;

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);

    Serializable s = expressionCache.getIfPresent(cacheKey);
    if (s == null) {
      s = MVEL.compileExpression(rule, parserContext);
      expressionCache.put(cacheKey, s);
    }

    Map<String, Object> vars = new HashMap<>();
    vars.put("order", order);
    vars.put("goodsIndex", goodsIndex);
    OptimizerFactory.setDefaultOptimizer(OptimizerFactory.SAFE_REFLECTIVE);
    Object result = MVEL.executeExpression(s, vars, myVarFactory);
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
      throw new ExpException("读取文件出错：" + fileName);
    }
  }
}

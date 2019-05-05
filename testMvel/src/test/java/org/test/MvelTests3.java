package org.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.test.model.Entry;
import org.test.model.EntryHead;
import org.test.model.EntryList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MvelTests3 {

  @Test
  public void test() {
    String expression = "( a > b + 2 ) && ( c < a - 5 )";
    ParserContext context = new ParserContext();
    Serializable compiledExpression = MVEL.compileExpression(expression, context);

    // Now the context will have a list of all the inputs.  Unfortunately it does not tell you what
    // type of Object the input is.
    for (@SuppressWarnings("rawtypes")
    Map.Entry<String, Class> entry : context.getInputs().entrySet()) {
      System.out.println(
          "Variable name : " + entry.getKey() + ", Data Type = " + entry.getValue().toString());
    }

    // Now, you can assign values to the data and run the expression.
    Map<String, Object> values = Maps.newHashMap();
    values.put("a", 25);
    values.put("b", 20);
    values.put("c", 10);

    // And we can get a boolean answer
    log.info(
        "Result of running formula with (a=25, b=20, c=10) = "
            + MVEL.executeExpression(compiledExpression, values, Boolean.class));
  }

  @Test
  public void test2() {

    String global =
        "def head(field) { "
            + "if (field==\"I_E_PORT\") "
            + "{return entry.entryHead.iqIEPort;}"
            + "};";
    String expression = "head(\"I_E_PORT\")";
    ParserContext context = new ParserContext();
    // context.setDebugSymbols(true);
    Serializable compiledExpression = MVEL.compileExpression(global + expression, context);

    Entry entry = initEntry();
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("MVEL", MVEL.class);
    vars.put("entry", entry);

    // And we can get a boolean answer
    log.info("Result of Test2: " + MVEL.executeExpression(compiledExpression, vars, String.class));
  }

  private Entry initEntry() {
    EntryHead head =
        EntryHead.builder()
            .consumerName("李志鹏")
            .exPort("0100")
            .declPort("0200")
            .dDate(LocalDateTime.now())
            .netWt(25.0)
            .iEPort("0101")
            .build();
    List<EntryList> lists = Lists.newArrayList();
    lists.add(
        EntryList.builder()
            .codeTs("8200210001")
            .gNo("01")
            .gName("计算机1")
            .customsValue(55.9)
            .build());
    lists.add(
        EntryList.builder()
            .codeTs("8400210001")
            .gNo("02")
            .gName("计算机2")
            .customsValue(50.8)
            .build());
    lists.add(
        EntryList.builder()
            .codeTs("8300210001")
            .gNo("03")
            .gName("计算机3")
            .customsValue(33.4)
            .build());

    Entry entry = Entry.builder().entryHead(head).entryList(lists).build();
    return entry;
  }

  @Test
  public void test3() {
    String global = "def hello() { System.out.println(\"Hello!\"); 25; }";
    String expression = "hello();";
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("MVEL", MVEL.class);

    ParserContext context = new ParserContext();
    // context.setDebugSymbols(true);
    Serializable compiledExpression = MVEL.compileExpression(global + expression, context);

    log.info("Result of Test3 " + MVEL.executeExpression(compiledExpression, vars));
  }

  @Test
  public void test4() {
    String func =
        "def HEAD(field) { \r\n"
            + "    if (field==\"I_E_PORT\") {return entry.entryHead.iqIEPort;}\r\n"
            + "};\r\n"
            + "\r\n"
            + "def LIST(field) { \r\n"
            + "    if (field==\"G_NO\") {return entry.entryList[0].gNo;}\r\n"
            + "};";

    VariableResolverFactory functionFactory = new MapVariableResolverFactory();
    MVEL.eval(func, functionFactory);

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);

    // ParserContext context = new ParserContext();

    Map<String, Object> vars = Maps.newHashMap();
    vars.put("MVEL", MVEL.class);
    vars.put("entry", Entry.class);

    Serializable s = MVEL.compileExpression("HEAD(\"I_E_PORT\") + LIST(\"G_NO\")", vars);

    Entry entry = initEntry();
    log.info("Result of Test4 ={}", MVEL.executeExpression(s, entry, myVarFactory));
  }

  @Test
  public void test7() {

    String func =
        "def HEAD(field) { \r\n"
            + "    if (field==\"I_E_PORT\") {return entry.entryHead.iqIEPort;}\r\n"
            + "};\r\n"
            + "\r\n"
            + "def LIST(field) { \r\n"
            + "    if (field==\"G_NO\") {return entry.entryList[0].gNo;}\r\n"
            + "};";

    //    VariableResolverFactory functionFactory = new MapVariableResolverFactory();
    //    MVEL.eval(func, functionFactory);

    String rule = "HEAD(\"I_E_PORT\") + LIST(\"G_NO\")";
    Entry entry = initEntry();

    Map<String, Object> vars = Maps.newHashMap();
    vars.put("MVEL", MVEL.class);
    vars.put("entry", Entry.class);

    Serializable s = MVEL.compileExpression(rule, vars);

    Object result = null;
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 100; i++) {
      VariableResolverFactory functionFactory = new MapVariableResolverFactory();
      MVEL.eval(func, functionFactory);
      VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
      myVarFactory.setNextFactory(functionFactory);
      result = MVEL.executeExpression(s, entry, myVarFactory);
    }

    log.info("test7:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
  }

  @Test
  public void test8() {

    String func = readFromFile();
    log.info(func);
    String rule = "HEAD(\"I_E_PORT\") + LIST(\"G_NO\")";
    Entry entry = initEntry();

    Map<String, Object> vars = Maps.newHashMap();
    vars.put("MVEL", MVEL.class);
    vars.put("entry", Entry.class);

    Serializable s = MVEL.compileExpression(func + rule, vars);

    Object result = null;
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 10; i++) {
      result = MVEL.executeExpression(s, entry, vars);
    }

    log.info("test6:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
  }

  @Test
  public void testFile() {
    log.info("file:{}", readFromFile());
  }

  private String readFromFile() {
    Resource resource = new ClassPathResource("entry.mvel");
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

      // 每次读取文件的缓存
      String temp = null;
      StringBuffer data = new StringBuffer();
      while ((temp = reader.readLine()) != null) {
        data.append(temp);
      }
      return data.toString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }
}

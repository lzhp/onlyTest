package org.test;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.test.hexpression.HExpException;
import org.test.model.Entry;
import org.test.model.EntryHead;
import org.test.model.EntryList;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseMvelTest {
  private static final ParserContext parserContext = new ParserContext();

  static {
    parserContext.addImport("MVEL", MVEL.class);
    parserContext.addInput("entry", Entry.class);
  }

  @Test
  public void testBase() {
    String rule = "HEAD(\"I_E_PORT\") ==\"0100\" && LIST(\"G_NO\") == \"03\"";

    Object result = null;
    Entry entry = initEntry();

    Serializable s = MVEL.compileExpression(rule, parserContext);

    Stopwatch watch = Stopwatch.createStarted();

    entry.setGoodsIndex(0);
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("entry", entry);

    for (int i = 0; i < 100; i++) {
      VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
      myVarFactory.setNextFactory(functionFactory);
      result = MVEL.executeExpression(s, vars, myVarFactory, boolean.class);
      log.info("{}", i);
    }

    log.info("test5:{},time:{}", result, watch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void testFunctionReuse() {
    VariableResolverFactory functionFactory = new MapVariableResolverFactory();
    MVEL.eval("def foo() { \"foo\"; }; def bar() { \"bar\" };", functionFactory);

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);

    Serializable s = MVEL.compileExpression("foo() + bar();");

    for (int i = 0; i < 10; i++) {
      assertEquals("foobar", MVEL.executeExpression(s, myVarFactory));
    }

    Serializable s1 = MVEL.compileExpression("foo() +bar()+ bar();");

    for (int i = 0; i < 10000; i++) {
      assertEquals("foobarbar", MVEL.executeExpression(s1, myVarFactory));
    }
  }

  private static final VariableResolverFactory functionFactory = new MapVariableResolverFactory();

  static {
    String entryFunc = readFromFile();
    MVEL.eval(entryFunc, functionFactory);
  }

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
}

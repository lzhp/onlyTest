/** Date: 2018-08-20 10:53:53. */
package org.test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.test.model.Entry;
import org.test.model.EntryHead;
import org.test.model.EntryList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

/**
 * Date: 2018-08-20 10:53:53.
 *
 * @author: lizhipeng.
 * @description:
 */
@Slf4j
public class MvelTests2 {

  @Test
  public void test6() {

    Entry entry = initEntry();

    // String expr = "tools.dateNow()";

    String expr =
        "entryHead.declPort == '0100' && HExp.dateNow() > '2018-08-20' && HExp.sumList(entryList, 'customsValue') > 100";

    ParserContext context = new ParserContext();
    context.addImport("HExp", org.test.hexpression.HExpTools.class);
    context.addInput("obj", org.test.model.Entry.class);
    Serializable compiled1 = MVEL.compileExpression(expr, context);

    Object result = null;
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 100000; i++) {
      result = MVEL.executeExpression(compiled1, entry);
    }

    log.info("test6:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
  }

  @Test
  public void test5() {

    Entry entry = initEntry();
    String expr = "HExp.sumList(entryList, 'aaaa') == 140.1 && 2> 1";
    // String expr = "tools.dateNow()";

    ParserContext context = new ParserContext();
    context.addImport("HExp", org.test.hexpression.HExpTools.class);
    context.addInput("obj", org.test.model.Entry.class);
    Serializable compiled1 = MVEL.compileExpression(expr, context);

    Object result = MVEL.executeExpression(compiled1, entry);

    log.info(result.toString());
  }

  @Test
  public void test1() {

    Entry entry = initEntry();
    String expr = "HExp.dateNow() + ' ' + HExp.timeNow() + ' ' + HExp.dateTimeNow()";

    ParserContext context = new ParserContext();
    context.addImport("HExp", org.test.hexpression.HExpTools.class);
    context.addInput("obj", org.test.model.Entry.class);
    Serializable compiled1 = MVEL.compileExpression(expr, context);

    Object result = MVEL.executeExpression(compiled1, entry);

    log.info(result.toString());
  }

  private Entry initEntry() {
    EntryHead head =
        EntryHead.builder()
            .consumerName("李志鹏")
            .declPort("0100")
            .imPort("0200")
            .dDate(LocalDateTime.now())
            .netWt(25.0)
            .build();
    List<EntryList> lists = Lists.newArrayList();
    lists.add(
        EntryList.builder().codeTs("8200210001").gNo("01").gName("计算机1").customsValue(55.9).build());
    lists.add(
        EntryList.builder().codeTs("8400210001").gNo("02").gName("计算机2").customsValue(50.8).build());
    lists.add(
        EntryList.builder().codeTs("8300210001").gNo("03").gName("计算机3").customsValue(33.4).build());

    Entry entry = Entry.builder().entryHead(head).entryList(lists).build();
    return entry;
  }
}

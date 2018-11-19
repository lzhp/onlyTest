/** Date: 2018-08-17 21:45:07. */
package org.test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.mvel2.MVEL;
import org.test.model.Entry;
import org.test.model.EntryHead;
import org.test.model.EntryList;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

/**
 * Date: 2018-08-17 21:45:07.
 *
 * @author: lizhipeng.
 * @description:
 */
@Slf4j
public class MvelTests {

  @Test
  public void test1() {

    Entry entry = initEntry();
    //String expr = "entryHead.iePort == '0100' && HExp.dateNow() > '2018-08-20' && HExp.sumList(entryList, 'tradeTotal') > 100";
    String expr = "tools.sumList(entryList, 'customsValue') == 140.1 && 2> 1";
    Object result = MVEL.eval(expr, entry);
    log.info(result.toString());
  }
  
  @Test
  public void test5() {
    /**
     * 测试null的支持
     */
    Entry entry = initEntry();
    entry.getEntryHead().setDeclPort(null);
    String expr = " entryHead.?declPort.substring(0,2) == '01' && 2> 1";
    Object result = MVEL.eval(expr, entry);
    log.info("test5()={}", result.toString());
    
    
  }

  @Test
  public void test2() {

    Entry entry = initEntry();
    String expr = "listSumTradeTotal==140.1 && entryHead.masterCustoms == '0100'";
    Object result = MVEL.eval(expr, entry);
    log.info(result.toString());
  }

  @Test
  public void test3() {

    int LOOP_TIMES = 1000*100;

    Entry entry = initEntry();
    String expr = "listSumTradeTotal==140.1 && entryHead.masterCustoms == '0100'";
    Serializable compiled = MVEL.compileExpression(expr);

    Object result = MVEL.executeExpression(compiled, entry);
    log.info(result.toString());

    Stopwatch watch = Stopwatch.createStarted();
    for (int i = 0; i < LOOP_TIMES; i++) {
      MVEL.executeExpression(compiled, entry);
    }
    log.info("time1: {}", watch.elapsed(TimeUnit.MILLISECONDS));

    result = MVEL.eval(expr, entry);
    log.info(result.toString());
    watch.reset();
    watch.start();
    for (int i = 0; i < LOOP_TIMES; i++) {
      MVEL.eval(expr, entry);
    }
    log.info("time2: {}", watch.elapsed(TimeUnit.MILLISECONDS));
  }
  
  @Test
  public void test6() {

    Entry entry = initEntry();
    String expr = "entryList[0].gName";
    Object result = MVEL.eval(expr, entry);
    log.info(result.toString());
  }

  private Entry initEntry() {
    EntryHead head =
        EntryHead.builder()
            .consumerName("李志鹏")
            .exPort("0100")
            .declPort("0200")
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

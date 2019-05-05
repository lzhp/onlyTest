package org.test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.test.hexpression.HExpEntry;
import org.test.model.Entry;
import org.test.model.EntryHead;
import org.test.model.EntryList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HExpEntryTests {

  @Test
  public void test() {
    String rule = "entry.goodsIndex ==0";

    Entry entry = initEntry();
    log.info("test resutl={}", (new HExpEntry()).check(rule, entry));
  }

  @Test
  public void test2() {
    String rule = "entry.entryHead.exPort ==\"0101\"";

    Entry entry = initEntry();
    log.info("test2 resutl={}", (new HExpEntry()).check(rule, entry));
  }

  @Test
  public void test3() {
    String rule = "HEAD(\"I_E_PORT\") ==\"0101\" && LIST(\"G_NO\") == \"0902\"";

    Entry entry = initEntry();
    log.info(
        "test3: \n entry: {}, \n rule: {}, \n resutl= {}", entry, rule, (new HExpEntry()).check(rule, entry));
  }

  @Test
  public void test4() {
    String rule = "HEAD(\"DECL_PORT\") ==\"0200\"";

    Entry entry = initEntry();
    log.info(
        "test4: \n entry: {}, \n rule: {}, \n resutl= {}", entry, rule, (new HExpEntry()).check(rule, entry));
  }
  
  
  @Test
  public void test5() {
    
    String rule = "HEAD(\"I_E_PORT\") ==\"0101\" && LIST(\"G_NO\") == \"0902\"";
    
    Entry entry = initEntry();
    
    Object result = null;
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 10; i++) {
      result = (new HExpEntry()).check(rule, entry);
    }

    log.info("test5:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
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
  public void testCheck() {
    String rule = "HEAD(\"I_E_PORT\") ==\"0100\" && LIST(\"G_NO\") == \"03\"";
    
    Object result = null;
    Entry entry = initEntry();
    result =(new HExpEntry()).check(rule, entry,0);
    
    
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 10000; i++) {
      result =(new HExpEntry()).check(rule, entry,0);
    }

    log.info("test5:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
  }
  
  @Test
  public void testCheck2() {
    String rule = "HEAD(\"I_E_PORT\") ==\"0101\" && LIST(\"G_NO\") == \"0902\"";
    
    Object result = null;
    Entry entry = initEntry();
    result =(new HExpEntry()).check2(rule, entry,0);
    
    
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 10000; i++) {
      result =(new HExpEntry()).check2(rule, entry,0);
    }

    log.info("test5:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
  }
  
  @Test
  public void testCheck3() {
    String rule = "HEAD(\"I_E_PORT\") ==\"0101\" && LIST(\"G_NO\") == \"0902\"";
    
    Entry entry = initEntry();
    
    Object result = null;
    StopWatch watch = StopWatch.createStarted();
    for (int i = 0; i < 10000; i++) {
      result =(new HExpEntry()).check3(rule, entry,0);
    }

    log.info("test5:{},time:{}", result, watch.getTime(TimeUnit.MILLISECONDS));
  }
}

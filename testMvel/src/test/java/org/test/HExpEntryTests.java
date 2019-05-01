package org.test;

import java.time.LocalDateTime;
import java.util.List;
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
    log.info("test resutl={}", HExpEntry.check(rule, entry));
  }

  @Test
  public void test2() {
    String rule = "entry.entryHead.exPort ==\"0101\"";

    Entry entry = initEntry();
    log.info("test2 resutl={}", HExpEntry.check(rule, entry));
  }

  @Test
  public void test3() {
    String rule = "HEAD(\"I_E_PORT\") ==\"0101\" && LIST(\"G_NO\") == \"0902\"";

    Entry entry = initEntry();
    log.info(
        "test3: \n entry: {}, \n rule: {}, \n resutl= {}", entry, rule, HExpEntry.check(rule, entry));
  }

  @Test
  public void test4() {
    String rule = "HEAD(\"DECL_PORT\") ==\"0200\"";

    Entry entry = initEntry();
    log.info(
        "test4: \n entry: {}, \n rule: {}, \n resutl= {}", entry, rule, HExpEntry.check(rule, entry));
  }
  private Entry initEntry() {
    EntryHead head =
        EntryHead.builder()
            .consumerName("李志鹏")
            .exPort("0100")
            .declPort("0200")
            .dDate(LocalDateTime.now())
            .netWt(25.0)
            .iqIEPort("0101")
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

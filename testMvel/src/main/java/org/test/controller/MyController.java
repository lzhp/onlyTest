/** Date: 2018-08-21 00:47:07. */
package org.test.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.model.Entry;
import org.test.model.EntryHead;
import org.test.model.EntryList;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

/**
 * Date: 2018-08-21 00:47:07.
 *
 * @author: lizhipeng.
 * @description:
 */
@RestController
@Slf4j
public class MyController {

  @RequestMapping("")
  public ResponseEntity<Entry> getCountries() {
    Entry entry = initEntry();
    log.debug(entry.toString());
    return new ResponseEntity<>(entry, HttpStatus.OK);
  }
  
  
  @RequestMapping("say")
  public ResponseEntity<Entry> sayHello(@RequestBody Entry entry) {
    log.debug(entry.toString());
    entry.getEntryHead().setDeclPort("0299");
    entry.getEntryList().get(0).setGName("bbbbb");
    return new ResponseEntity<>(entry, HttpStatus.OK);
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

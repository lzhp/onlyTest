package com.example.demo;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import com.example.demo.exp.ExpOrder;
import com.example.demo.model.Order;
import com.example.demo.model.OrderHead;
import com.example.demo.model.OrderList;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpOrderTests {

  final int times = 100000;

  @Test
  public void testExpUtil() {

    String rule = "";
    Order order = initOrder();
    boolean result = false;

    rule = "weekDay()==7";
    result = (new ExpOrder()).evalToBoolean(rule, order, 0);
    Assert.assertTrue(result == true);

    rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"G_NO\") == \"02\"";
    result = (new ExpOrder()).evalToBoolean(rule, order, 1);
    Assert.assertTrue(result == true);
    
    rule = "mid(HEAD(\"POST_CODE\"),2,1)==\"1\"";
    result = (new ExpOrder()).evalToBoolean(rule, order, 0);
    Assert.assertTrue(result == true);
  }

  @Test
  public void testExpUtilmid() {

    String rule = "";
    Order order = initOrder();
    String result = "";

    rule = "mid(\"test\", 2, 1)";
    result = (new ExpOrder()).evalToString(rule, order, 0);
    log.info("[{}]", result);
    Assert.assertTrue(result == "e");

    rule = "mid(\"test\", 2, 1)==\"e\"";
    result = (new ExpOrder()).evalToString(rule, order, 0);
    Assert.assertTrue(result == "true");
  }

  @Test
  public void testFunc() {

    String rule = "";
    Order order = initOrder();
    boolean result = false;

    rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"G_NO\") == \"02\"";
    result = (new ExpOrder()).evalToBoolean(rule, order, 0);
    Assert.assertTrue(result == false);

    rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"G_NO\") == \"02\"";
    result = (new ExpOrder()).evalToBoolean(rule, order, 1);
    Assert.assertTrue(result == true);
  }

  @Test
  public void testCodeTs() {

    String rule = "";
    Order order = initOrder();
    boolean result = false;

    rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"CODE_TS\") == \"1234567890\"";
    result = (new ExpOrder()).evalToBoolean(rule, order, 0);
    Assert.assertTrue(result == true);

    rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"CODE_TS\") == \"3456789012\"";
    result = (new ExpOrder()).evalToBoolean(rule, order, 2);
    Assert.assertTrue(result == true);
  }

  @Test
  public void testPerf2() {
    String rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"G_NO\") == \"01\"";

    boolean result = false;
    Order order = initOrder();
    result = (new ExpOrder()).evalToBoolean(rule, order, 0);

    Stopwatch watch = Stopwatch.createStarted();
    for (int i = 0; i < times; i++) {
      result = (new ExpOrder()).evalToBoolean(rule, order, 0);
    }

    log.info("testPerf2, rule:{}, result:{}", rule, result);
    log.info("testPerf2, times:{},time:{}ms", times, watch.elapsed(TimeUnit.MILLISECONDS));
    Assert.assertTrue(result);
  }

  @Test
  public void testPerf() {
    String rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"G_NO\") == \"02\"";

    boolean result = false;
    Order order = initOrder();
    result = (new ExpOrder()).evalToBoolean(rule, order, 0);

    Stopwatch watch = Stopwatch.createStarted();
    for (int i = 0; i < times; i++) {
      result = (new ExpOrder()).evalToBoolean(rule, order, 0);
    }

    log.info("testPerf, rule:{}, result:{}", rule, result);
    log.info("testPerf, times:{},time:{}ms", times, watch.elapsed(TimeUnit.MILLISECONDS));
    Assert.assertTrue(result == false);
  }

  private Order initOrder() {
    List<OrderList> list = Lists.newArrayList();
    list.add(OrderList.builder().codeTs("1234567890").gName("computer1").gNo("01").build());
    list.add(OrderList.builder().codeTs("2345678901").gName("computer2").gNo("02").build());
    list.add(OrderList.builder().codeTs("3456789012").gName("computer3").gNo("03").build());

    OrderHead head =
        OrderHead.builder().orderId("111").customerName("bill gates").postCode("0101").build();

    return Order.builder().orderHead(head).orderList(list).build();
  }
}

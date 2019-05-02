package com.example.demo;

import static org.junit.Assert.assertTrue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import com.example.demo.model.Order;
import com.example.demo.model.OrderHead;
import com.example.demo.model.OrderList;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseTests {

  @Test
  public void testBase() {
    String func = "def HEAD(field) { \r\n" + 
        "    if (field==\"POST_CODE\") {return order.?orderHead.?postCode;}\r\n" + 
        "    else if (field==\"CUSTOMER_NAME\") {return order.?orderHead.?customerName;}\r\n" + 
        "};\r\n" + 
        "\r\n" + 
        "def LIST(field) { \r\n" + 
        "    if (field==\"G_NO\") {return order.?orderList[order.goodsIndex].?gNo;}\r\n" + 
        "};";
    
    VariableResolverFactory functionFactory = new MapVariableResolverFactory();
    MVEL.eval(func, functionFactory);
    
    String rule = "HEAD(\"POST_CODE\") ==\"0101\" && LIST(\"G_NO\") == \"01\"";


    Serializable s = MVEL.compileExpression(rule);
    Stopwatch watch = Stopwatch.createStarted();

    Order order = initOrder();    
    order.setGoodsIndex(0);
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("order", order);

    boolean result = false;

    for (int i = 0; i < 100; i++) {
      VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
      myVarFactory.setNextFactory(functionFactory);
      result = MVEL.executeExpression(s, vars, myVarFactory, boolean.class);
      log.info("{}", i);
      assertTrue(result);
    }

    log.info("testBase:{},time:{}", result, watch.elapsed(TimeUnit.MILLISECONDS));
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

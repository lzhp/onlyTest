package org.test.query;

import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderByItemTests {

  @Test
  public void testToSqlString() {
    OrderByItem i = new OrderByItem("test", OrderByItem.Direction.DESC);
    log.info(i.toSqlString());
    
    OrderByItem b = OrderByItem.builder().property("go").build();
    log.info(b.toSqlString());
  }

}

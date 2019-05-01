/** Date: 2018-08-20 21:01:57. */
package org.test;

import org.junit.Assert;
import org.junit.Test;
import org.test.hexpression.HExpTools;
import lombok.extern.slf4j.Slf4j;

/**
 * Date: 2018-08-20 21:01:57.
 *
 * @author: lizhipeng.
 * @description:
 */
@Slf4j
public class HExpTests {

  @Test
  public void test() {
    log.debug(HExpTools.dateNow());
    log.debug("test:{}", HExpTools.in(123, 123, 4, 5, 789));
    log.debug("test:{}", HExpTools.in(123, 23, 4, 5, 789));

    Assert.assertTrue(HExpTools.in(123, 123, 4, 5, 789));
    Assert.assertFalse(HExpTools.in(123, 23, 4, 5, 789));
  }

  @Test
  public void test2() {

    log.debug("test2:{}", HExpTools.bt(123, 123, 156, 678, 789));
    log.debug("test2:{}", HExpTools.bt(4.0, 123, 156, 678, 789, 4.0));
    Assert.assertTrue(HExpTools.bt(123, 123, 156, 678, 789));
    Assert.assertTrue(HExpTools.bt(4.0, 123, 156, 678, 789, 4.0));
    Assert.assertTrue(HExpTools.bt("2018-08-12", "2018-08-01", "2018-08-13"));
    Assert.assertTrue(HExpTools.bt("12:12:00", "12:01:00", "12:30:00"));
  }
}

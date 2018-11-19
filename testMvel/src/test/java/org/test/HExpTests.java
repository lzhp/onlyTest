/** Date: 2018-08-20 21:01:57. */
package org.test;

import org.junit.Assert;
import org.junit.Test;
import org.test.hexpression.HExp;
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
    log.debug(HExp.dateNow());
    log.debug("test:{}", HExp.in(123, 123, 4, 5, 789));
    log.debug("test:{}", HExp.in(123, 23, 4, 5, 789));

    Assert.assertTrue(HExp.in(123, 123, 4, 5, 789));
    Assert.assertFalse(HExp.in(123, 23, 4, 5, 789));
  }

  @Test
  public void test2() {

    log.debug("test2:{}", HExp.bt(123, 123, 156, 678, 789));
    log.debug("test2:{}", HExp.bt(4.0, 123, 156, 678, 789, 4.0));
    Assert.assertTrue(HExp.bt(123, 123, 156, 678, 789));
    Assert.assertTrue(HExp.bt(4.0, 123, 156, 678, 789, 4.0));
    Assert.assertTrue(HExp.bt("2018-08-12", "2018-08-01", "2018-08-13"));
    Assert.assertTrue(HExp.bt("12:12:00", "12:01:00", "12:30:00"));
  }
}

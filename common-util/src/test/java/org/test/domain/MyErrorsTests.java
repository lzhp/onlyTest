/**
 * Date: 2018-04-30 22:50:45.
 */
package org.test.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.test.exception.BaseException;
import org.test.exception.BusinessException;
import org.test.exception.CommonException;
import org.test.exception.PermissionException;
import org.test.exception.errors.PermissionErrors;
import org.test.exception.errors.TestErrors;
import lombok.extern.slf4j.Slf4j;

/**
 * Date: 2018-04-30 22:50:45.
 * 
 * @author: lizhipeng.
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MyErrorsTests {

  @Test
  public void test() {
    log.info("{}", TestErrors.COMMON_ERROR);

    BaseException t = new CommonException(TestErrors.COMMON_ERROR);

    log.error("{}", t.toString(), t);
    log.error("fff", t);
  }

  @Test
  public void testNest() {
    BaseException t = new CommonException(TestErrors.COMMON_ERROR);
    BusinessException be = new BusinessException(TestErrors.UNKNOW_ERROR, "", t);

    log.error("出现了异常", be);
  }


  @Test
  public void testNest2() {

    try {
      int i = 3 / 0;
      log.info("{}", i);
    } catch (Exception e) {
      BaseException t = new CommonException(TestErrors.COMMON_ERROR, "bizId", "common Message", e);
      BaseException be =
          new BusinessException(TestErrors.UNKNOW_ERROR, "id", "business message", t);
      BaseException be2 =
          new BusinessException(TestErrors.UNKNOW_ERROR, "id2", "business message 3", be);

      log.error("出现了异常{}", be2.toString(), be2);
    }
  }

  @Test
  public void test2() {
    // log.info("{}", TestErrors.COMMON_ERROR);

    PermissionException t = new PermissionException(PermissionErrors.NO_PERMISSION);
    t.setAddtionalMessage("addtional message!");

    log.error("{}", t.toString(), t);
    // log.error("fff", t);
  }

}


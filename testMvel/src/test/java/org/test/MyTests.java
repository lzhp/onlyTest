/** Date: 2018-07-16 19:02:35. */
package org.test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

/**
 * Date: 2018-07-16 19:02:35.
 *
 * @author: lizhipeng.
 * @description:
 */
@Slf4j
public class MyTests {

  @Test
  public void test() throws UnsupportedEncodingException {
    String t = "—";
    byte[] bytes = t.getBytes("GB2312");
    log.info(Arrays.toString(bytes));
    String tt = new String(bytes, "GB2312");
    log.info(tt);
  }
  
  
  @org.junit.Test
  public void test6() throws UnsupportedEncodingException {
    String t = "‖";
    byte[] bytes = t.getBytes("GB2312");
    log.info(Arrays.toString(bytes));
    String tt = new String(bytes, "GB2312");
    log.info(tt);
  }
  
  @org.junit.Test
  public void test3() throws UnsupportedEncodingException {
    String t = "々";
    byte[] bytes = t.getBytes("GBK");
    log.info(Arrays.toString(bytes));
    String tt = new String(bytes, "GBK");
    log.info(tt);
  }

  @org.junit.Test
  public void test2() throws UnsupportedEncodingException {
    String t = "中";
    byte[] bytes = t.getBytes("GB2312");
    log.info(Arrays.toString(bytes));
    String tt = new String(bytes, "GB2312");
    log.info(tt);
  }
}

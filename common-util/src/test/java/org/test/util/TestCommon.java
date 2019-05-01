package org.test.util;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestCommon {

  @Test
  public void testTrimString() {
    String s = null;

    Assert.assertTrue(CommonUtil.trimString(s).equals(""));

    s = "abc     ";
    Assert.assertTrue(CommonUtil.trimString(s).equals("abc"));

    s = "abc     \n bbb";
    Assert.assertTrue(CommonUtil.trimString(s).equals("abc     \n bbb"));

    s = "abc\n";
    Assert.assertTrue(CommonUtil.trimString(s).equals("abc"));
  }

  @Test
  public void testLimitStringLength() {
    String s = null;

    Assert.assertEquals("          ", CommonUtil.limitStringLength(s, 10));

    s = "12345678901234567890";
    Assert.assertEquals("1234567890", CommonUtil.limitStringLength(s, 10));
    Assert.assertEquals(10, CommonUtil.limitStringLength(s, 10).length());

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三四五六七八九十", CommonUtil.limitStringLength(s, 10));

    Assert.assertEquals(10, CommonUtil.limitStringLength(s, 10).length());
  }

  @Test
  public void testStringLenthGBK() {
    String s = null;

    Assert.assertEquals(0, CommonUtil.stringLengthGBKBytes(s));

    s = "12345678901234567890";
    Assert.assertEquals(20, CommonUtil.stringLengthGBKBytes(s));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals(60, CommonUtil.stringLengthGBKBytes(s));

    s = "一二三四五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals(65, CommonUtil.stringLengthGBKBytes(s));
  }

  @Test
  public void testLimitStringLenthGBK() {
    String s = null;
    Assert.assertEquals("          ", CommonUtil.limitStringLengthGBK(s, 10));

    s = "12345678901234567890";
    Assert.assertEquals("1234567890", CommonUtil.limitStringLengthGBK(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三四五", CommonUtil.limitStringLengthGBK(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("一二三四a ", CommonUtil.limitStringLengthGBK(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("", CommonUtil.limitStringLengthGBK(s, 0));
  }

  @Test
  public void testLimitStringLenthUTF8() {
    String s = null;
    Assert.assertEquals(Strings.padEnd("", 10, ' '), CommonUtil.exactStringLengthUTF8(s, 10));

    s = "12345678901234567890";
    Assert.assertEquals("1234567890", CommonUtil.exactStringLengthUTF8(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三 ", CommonUtil.exactStringLengthUTF8(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("一二三 ", CommonUtil.exactStringLengthUTF8(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("", CommonUtil.exactStringLengthUTF8(s, 0));
  }

  @Test
  public void UTF8trunc() {
    String s = null;
    // Assert.assertEquals(Strings.padEnd("", 10, ' '), CommonUtil.utf8truncate(s, 10));

    s = "12345678901234567890";
    Assert.assertEquals("1234567890", CommonUtil.utf8truncate(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三 ", CommonUtil.utf8truncate(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("一二三 ", CommonUtil.utf8truncate(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("", CommonUtil.utf8truncate(s, 0));
  }

  @Test
  public void test3() {
    String s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三 ", CommonUtil.exactStringLengthUTF8(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三", CommonUtil.exactStringLengthUTF8(s, 9));

    s = "一二a三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二a三", CommonUtil.exactStringLengthUTF8(s, 10));

    s = "一二a三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二a三 ", CommonUtil.exactStringLengthUTF8(s, 11));
  }

  @Test
  public void test4() {

    int count = 10000000;
    String s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Stopwatch watch = Stopwatch.createStarted();

    for (int i = 0; i < count; i++) {
      CommonUtil.exactStringLengthUTF8(s, 10);
    }
    log.debug("first:{}", watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      CommonUtil.limitStringLengthUTF8(s, 10);
    }

    log.debug("second:{}", watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      CommonUtil.utf8truncate(s, 10);
    }

    log.debug("third:{}", watch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void test5() {

    int count = 10000;
    String s =
        "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    int length = 90;
    String temp = "";

    log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);
    Stopwatch watch = Stopwatch.createStarted();

    for (int i = 0; i < count; i++) {
      temp = CommonUtil.exactStringLengthUTF8(s, length);
    }
    log.debug("first: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.limitStringLengthUTF8(s, length);
    }

    log.debug("secnd: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.utf8truncate(s, length);
    }

    log.debug("third: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void test6() {

    log.info("test6");

    int count = 1000;
    String s =
        "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    int length = 4000;
    String temp = "";
    Stopwatch watch = Stopwatch.createStarted();

    log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);

    for (int i = 0; i < count; i++) {
      temp = CommonUtil.exactStringLengthUTF8(s, length);
    }
    log.debug("first: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.limitStringLengthUTF8(s, length);
    }

    log.debug("secnd: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.utf8truncate(s, length);
    }

    log.debug("third: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.utf8truncate2(s, length);
    }

    log.debug("fouth: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.utf8truncate3(s, length);
    }

    log.debug("fivth: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void test7() {

    log.info("test7");

    int count = 100;
    String s =
        "ℤ一ﾖ二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八";
    int length = 9;
    String temp = "";

    log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);

    Stopwatch watch = Stopwatch.createStarted();

    for (int i = 0; i < count; i++) {
      temp = CommonUtil.exactStringLengthUTF8(s, length);
    }
    log.debug("first: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.limitStringLengthUTF8(s, length);
    }

    log.debug("secnd: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));

    watch.reset();
    watch.start();
    for (int i = 0; i < count; i++) {
      temp = CommonUtil.utf8truncate(s, length);
    }

    log.debug("third: result-[{}], time-{}", temp, watch.elapsed(TimeUnit.MILLISECONDS));
  }

  @Test
  public void test8() {

    log.info("test8");

    // int count = 100;
    String s = "ℤ一ﾖ √®∑øでも底に冷たさをもつ青いそら 也算是";
    // String s = "\u2F81A";
    // int length = 9;
    String temp0 = "";
    String temp1 = "";
    String temp2 = "";

    // log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);

    for (int i = 0; i < 50; i++) {
      log.info("{}", i);
      temp0 = CommonUtil.exactStringLengthUTF8(s, i);
      log.info("first:{}", temp0);
      temp1 = CommonUtil.limitStringLengthUTF8(s, i);
      log.info("secnd:{}", temp1);
      temp2 = CommonUtil.utf8truncate(s, i);
      log.info("third:{}", temp2);

      Assert.assertEquals(temp0, temp1);
      Assert.assertEquals(temp0, temp2);

      log.info("go");
    }
  }

  @Test
  public void test9() {

    log.info("test9");

    // int count = 100;
    // String s = "ℤ一ﾖ √®∑øでも底に冷たさをもつ青いそら 也算是";
    String s = "a𝄞a";
    log.info("s:{}", s);

    log.info("codpoint: {}", Integer.toHexString(s.codePointAt(0)));
    log.info("charAt  : {}", Integer.toHexString(s.charAt(0)));

    log.info("from code point: {}", Character.toChars(s.codePointAt(0)));

    log.info("codpoint: {}", Integer.toHexString(s.codePointAt(1)));
    log.info("charAt  : {}", Integer.toHexString(s.charAt(1)));

    // int length = 9;
    String temp0 = "";
    String temp1 = "";
    String temp2 = "";
    String temp3 = "";
    int i = 4;

    // log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);

    // for (int i = 0; i < 50; i++) {
    log.info("{}", i);
    temp0 = CommonUtil.exactStringLengthUTF8(s, i);
    log.info("first:{}", temp0);
    temp1 = CommonUtil.limitStringLengthUTF8(s, i);
    log.info("secnd:{}", temp1);
    temp2 = CommonUtil.utf8truncate(s, i);
    log.info("third:{}", temp2);
    temp3 = CommonUtil.utf8truncate2(s, i);
    log.info("third:{}", temp3);

    // Assert.assertEquals(temp0, temp1);
    Assert.assertEquals(temp0, temp2);
    Assert.assertEquals(temp0, temp3);

    log.info("go");
    // }
  }

  @Test
  public void test10() {

    log.info("test9");

    // int count = 100;
    // String s = "ℤ一ﾖ √®∑øでも底に冷たさをもつ青いそら 也算是";
    String s = "a𝄞a";
    log.info("s:{}", s);

    log.info("codpoint: {}", Integer.toHexString(s.codePointAt(0)));
    log.info("charAt  : {}", Integer.toHexString(s.charAt(0)));

    log.info("from code point: {}", Character.toChars(s.codePointAt(0)));

    log.info("codpoint: {}", Integer.toHexString(s.codePointAt(1)));
    log.info("charAt  : {}", Integer.toHexString(s.charAt(1)));

    // int length = 9;
    String temp0 = "";
    String temp1 = "";
    String temp2 = "";
    String temp3 = "";
    int i = 9;

    // log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);

    // for (int i = 0; i < 50; i++) {
    log.info("{}", i);
    temp0 = CommonUtil.exactStringLengthUTF8(s, i);
    log.info("first:{}", temp0);
    temp1 = CommonUtil.limitStringLengthUTF8(s, i);
    log.info("secnd:{}", temp1);
    temp2 = CommonUtil.utf8truncate(s, i);
    log.info("third:{}", temp2);
    temp3 = CommonUtil.utf8truncate2(s, i);
    log.info("fouth:{}", temp3);

    // Assert.assertEquals(temp0, temp1);
    Assert.assertEquals(temp0, temp2);
    Assert.assertEquals(temp0, temp3);

    log.info("go");
    // }

  }

  @Test
  public void test11() {

    log.info("test11");

    // int count = 100;
    // String s = "ℤ一ﾖ √®∑øでも底に冷たさをもつ青いそら 也算是";
    // String s = "a\uD835\uDD0Aa";
    // String s = "a\u0915\u094D\u0924\u0941a";
    String s = "\ud83d\udcdb"; // #$D83D#$DCDB  📛
    log.info(
        "s:{}, s.codePointCount:{}, s.length:{}, s.chars().count:{}",
        s,
        s.codePointCount(0, s.length()),
        s.length(),
        s.chars().count());

    try {
      log.info(
          "s.utf16:{}, s.codePointCount:{}, s.length:{}, s.chars().count:{}",
          Hex.encodeHexString(s.getBytes("UTF16")),
          s.codePointCount(0, s.length()),
          s.length(),
          s.chars().count());
    } catch (UnsupportedEncodingException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    try {
      log.info("s.utf8length:{}, s={}", CommonUtil.stringLengthUTF8Bytes(s), s.getBytes("UTF8"));
    } catch (UnsupportedEncodingException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }

    log.info("codpoint: {}", Integer.toHexString(s.codePointAt(0)));
    log.info("charAt  : {}", Integer.toHexString(s.charAt(0)));

    log.info("from code point: {}", Character.toChars(s.codePointAt(0)));

    log.info("codpoint: {}", Integer.toHexString(s.codePointAt(1)));
    log.info("charAt  : {}", Integer.toHexString(s.charAt(1)));

    // int length = 9;
    String temp0 = "";
    String temp1 = "";
    String temp2 = "";
    String temp3 = "";
    int i = 7;

    // log.info("loop times: {}, origin length: {}, require Length: {}", count, s.length(), length);

    // for (int i = 0; i < 50; i++) {
    log.info("{}", i);
    temp0 = CommonUtil.exactStringLengthUTF8(s, i);
    log.info("first:{}", temp0);
    temp1 = CommonUtil.limitStringLengthUTF8(s, i);
    log.info("secnd:{}", temp1);
    temp2 = CommonUtil.utf8truncate(s, i);
    log.info("third:{}", temp2);
    temp3 = CommonUtil.utf8truncate2(s, i);
    log.info("fouth:{}", temp3);

    // Assert.assertEquals(temp0, temp1);
    Assert.assertEquals(temp0, temp2);
    Assert.assertEquals(temp0, temp3);

    s = temp0;
    log.info(
        "s:{}, s.codePointCount:{}, s.length:{}, s.chars().count:{}",
        s,
        s.codePointCount(0, s.length()),
        s.length(),
        s.chars().count());

    try {
      log.info("s.utf8length:{}, s={}", CommonUtil.stringLengthUTF8Bytes(s), s.getBytes("UTF8"));
    } catch (UnsupportedEncodingException e) { // TODO Auto-generated catch block
      e.printStackTrace();
    }
    try {
      log.info(
          "s.utf16:{}, s.codePointCount:{}, s.length:{}, s.chars().count:{}",
          Hex.encodeHexString(s.getBytes("UTF16")),
          s.codePointCount(0, s.length()),
          s.length(),
          s.chars().count());
    } catch (UnsupportedEncodingException e1) { // TODO Auto-generated catch block
      e1.printStackTrace();
    }

    log.info("go");
    // }

  }
}

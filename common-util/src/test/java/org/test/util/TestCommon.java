package org.test.util;

import org.junit.Assert;
import org.junit.Test;
import com.google.common.base.Strings;


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

    Assert.assertEquals("", CommonUtil.limitStringLength(s, 10));

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
    Assert.assertEquals("", CommonUtil.limitStringLengthGBK(s, 10));

    s = "12345678901234567890";
    Assert.assertEquals("1234567890", CommonUtil.limitStringLengthGBK(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三四五", CommonUtil.limitStringLengthGBK(s, 10));


    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("一二三四a", CommonUtil.limitStringLengthGBK(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("", CommonUtil.limitStringLengthGBK(s, 0));
  }


  @Test
  public void testLimitStringLenthUTF8() {
    String s = null;
    Assert.assertEquals(Strings.padEnd("", 10, ' '), CommonUtil.limitStringLengthUTF82(s, 10));

    s = "12345678901234567890";
    Assert.assertEquals("1234567890", CommonUtil.limitStringLengthUTF82(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三 ", CommonUtil.limitStringLengthUTF82(s, 10));


    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("一二三 ", CommonUtil.limitStringLengthUTF82(s, 10));

    s = "一二三四a五六七八九十一二三四，五六七八九十一二三四五六七八九十abc";
    Assert.assertEquals("", CommonUtil.limitStringLengthUTF82(s, 0));
  }

  @Test
  public void test3() {
    String s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三 ", CommonUtil.limitStringLengthUTF82(s, 10));

    s = "一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二三", CommonUtil.limitStringLengthUTF82(s, 9));

    s = "一二a三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二a三", CommonUtil.limitStringLengthUTF82(s, 10));

    s = "一二a三四五六七八九十一二三四五六七八九十一二三四五六七八九十";
    Assert.assertEquals("一二a三 ", CommonUtil.limitStringLengthUTF82(s, 11));
  }
}

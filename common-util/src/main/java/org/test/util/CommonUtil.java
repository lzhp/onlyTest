package org.test.util;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Utf8;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

  /**
   * 转换为字符串，将null转化为空字符串""
   *
   * @param originalString
   * @return
   */
  public static String converToString(String originalString) {
    return Strings.nullToEmpty(originalString);
  }

  /**
   * 去除字符串的两侧空白
   *
   * @param originalString
   * @return
   */
  public static String trimString(String originalString) {
    return Strings.nullToEmpty(originalString).trim();
  }

  /**
   * 限制字符串长度（unicode字符数）
   *
   * @param originalString
   * @param maxLength
   * @return
   */
  public static String limitStringLength(String originalString, int maxLength) {
    String result = Strings.nullToEmpty(originalString);
    if (result.length() > maxLength) {
      return result.substring(0, maxLength);
    } else {
      return Strings.padEnd(result, maxLength, ' ');
    }
  }

  /**
   * 限制字符串长度（utf8字节长度），不足位数补空格
   *
   * @param originalString
   * @param maxUTF8BytesLength
   * @return
   */
  public static String limitStringLengthUTF8(String originalString, int maxUTF8BytesLength) {
    String temp = Strings.nullToEmpty(originalString);

    String result = temp;
    if (stringLengthUTF8Bytes(temp) > maxUTF8BytesLength) {
      for (int i = temp.length(); i >= 0; i--) {
        result = temp.substring(0, i);
        if (stringLengthUTF8Bytes(result) <= maxUTF8BytesLength)
          break;
      }
    }

    while (stringLengthUTF8Bytes(result) < maxUTF8BytesLength) {
      result = result + " ";
    }

    return result;
  }

  /**
   * 限制字符串长度（utf8字节长度），不足位数补空格
   *
   * @param originalString
   * @param maxUTF8BytesLength
   * @return
   */
  public static String limitStringLengthUTF82(String originalString, int maxUTF8BytesLength) {

    if (Strings.isNullOrEmpty(originalString)) {
      return Strings.padEnd("", maxUTF8BytesLength, ' ');
    }
    
    if (maxUTF8BytesLength==0) {
      return "";
    }else if (maxUTF8BytesLength<0) {
      return Strings.nullToEmpty(originalString);
    }
    
    String result = originalString;
    int i = 0;
    if (stringLengthUTF8Bytes(originalString) > maxUTF8BytesLength) {

      try {
        result = new String(originalString.getBytes("UTF8"), 0, maxUTF8BytesLength, "UTF8");

        log.debug("well{}", Utf8.isWellFormed(result.getBytes("UTF8")));
        i = result.length() - 1;
        log.debug("i:{}", i);
        log.debug("result:{}", result.codePointAt(i));
        log.debug("source:{}", originalString.codePointAt(i));
        if (!Objects.equals(result.codePointAt(i), originalString.codePointAt(i))) {
          result = result.substring(0, i);
        }
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    log.debug("{}", stringLengthUTF8Bytes(result));
    if (stringLengthUTF8Bytes(result) < maxUTF8BytesLength) {
      result = Strings.padEnd(result,
          result.length() + maxUTF8BytesLength - stringLengthUTF8Bytes(result), ' ');
    }

    return result;
  }

  /**
   * 返回字符串长度，按UTF8编码
   *
   * @param originalString
   * @return
   */
  public static int stringLengthUTF8Bytes(String originalString) {
    try {
      return Strings.nullToEmpty(originalString).getBytes("UTF8").length;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 限制字符串长度（GBK字节长度），不足位数补空格
   *
   * @param originalString
   * @param maxGBBytesLength
   * @return
   */
  public static String limitStringLengthGBK(String originalString, int maxGBBytesLength) {
    String temp = Strings.nullToEmpty(originalString);

    String result = temp;
    if (stringLengthGBKBytes(temp) > maxGBBytesLength) {
      for (int i = temp.length(); i >= 0; i--) {
        result = temp.substring(0, i);
        if (stringLengthGBKBytes(result) <= maxGBBytesLength)
          break;
      }
    }

    while (stringLengthGBKBytes(result) < maxGBBytesLength) {
      result = result + " ";
    }

    return result;
  }

  /**
   * 返回字符串长度，按GB2312编码
   *
   * @param originalString
   * @return
   */
  public static int stringLengthGBKBytes(String originalString) {
    try {
      return Strings.nullToEmpty(originalString).getBytes("GB2312").length;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 数组转化为List
   *
   * @param array
   * @return
   */
  public static <T> List<T> array2List(T[] array) {
    List<T> result = Lists.newArrayList();
    Collections.addAll(result, array);
    return result;


  }

  /**
   * 
   * @param input
   * @param length
   * @return
   */
  public static String utf8truncate(String input, int length) {
    StringBuffer result = new StringBuffer(length);
    int resultlen = 0;
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      int charlen = 0;
      if (c <= 0x7f) {
        charlen = 1;
      } else if (c <= 0x7ff) {
        charlen = 2;
      } else if (c <= 0xd7ff) {
        charlen = 3;
      } else if (c <= 0xdbff) {
        charlen = 4;
      } else if (c <= 0xdfff) {
        charlen = 0;
      } else if (c <= 0xffff) {
        charlen = 3;
      }
      if (resultlen + charlen > length) {
        break;
      }
      result.append(c);
      resultlen += charlen;
    }
    return result.toString();
  }
}
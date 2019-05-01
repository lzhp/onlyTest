package org.test.util;

/** @author lizhipeng */
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;
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
        if (stringLengthUTF8Bytes(result) <= maxUTF8BytesLength) {
          break;
        }
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
  public static String exactStringLengthUTF8(String originalString, int maxUTF8BytesLength) {

    if (Strings.isNullOrEmpty(originalString)) {
      return Strings.padEnd("", maxUTF8BytesLength, ' ');
    }

    if (maxUTF8BytesLength == 0) {
      return "";
    } else if (maxUTF8BytesLength < 0) {
      return Strings.nullToEmpty(originalString);
    }

    String result = originalString;
    int i = 0;
    if (stringLengthUTF8Bytes(originalString) > maxUTF8BytesLength) {
      try {
        result = new String(originalString.getBytes("UTF8"), 0, maxUTF8BytesLength, "UTF8");
      } catch (UnsupportedEncodingException e) {
        // will not get here
        log.error("", e);
        return "";
      }
      // last character
      i = result.length() - 1;
      //        log.debug("i:{}", i);
      //        log.debug("last of result:{}", result.codePointAt(i));
      //        log.debug("same of source:{}", originalString.codePointAt(i));
      if (result.codePointAt(i) != originalString.codePointAt(i)) {
        // half word of Chinese etc, trim last character
        result = result.substring(0, i);
      }
    }
    //    log.debug("{}", stringLengthUTF8Bytes(result));
    if (stringLengthUTF8Bytes(result) < maxUTF8BytesLength) {
      result =
          Strings.padEnd(
              result, result.length() + maxUTF8BytesLength - stringLengthUTF8Bytes(result), ' ');
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
        if (stringLengthGBKBytes(result) <= maxGBBytesLength) {
          break;
        }
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
   * @param input
   * @param length
   * @return
   */
  public static String utf8truncate(String input, int length) {

    /**
     * https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html the first from the
     * high-surrogates range, (U+D800 to U+DBFF), the second from the low-surrogates range (U+DC00
     * to U+DFFF)
     */
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

    String temp = result.toString();

    if (stringLengthUTF8Bytes(temp) < length) {
      temp = Strings.padEnd(temp, temp.length() + length - stringLengthUTF8Bytes(temp), ' ');
    }

    return temp;
  }

  public static String utf8truncate2(String input, int length) {
    StringBuffer result = new StringBuffer(length);
    int resultlen = 0;
    for (int i = 0; i < input.length(); i++) {
      int c = input.codePointAt(i);
      //String temp = Integer.toHexString(c);
      int charlen = 0;
      if (c <= 0x7f) {
        charlen = 1;
      } else if (c <= 0x7ff) {
        charlen = 2;
      } else if (c <= 0xffff) {
        charlen = 3;
      } else if (c < 0x1fffff) {
        charlen = 4;
      }
      if (resultlen + charlen > length) {
        break;
      }

      if (Character.isBmpCodePoint(c)) {
        result.append((char) c);
      } else {
        result.append(Character.highSurrogate(c));
        result.append(Character.lowSurrogate(c));
        i++;
      }
      // result.append(Character.toChars(c));
      resultlen += charlen;
    }

    String temp = result.toString();

    if (stringLengthUTF8Bytes(temp) < length) {
      temp = Strings.padEnd(temp, temp.length() + length - stringLengthUTF8Bytes(temp), ' ');
    }

    return temp;
  }

  public static String utf8truncate3(String input, int length) {
    StringBuffer result = new StringBuffer(length);
    int resultlen = 0;
    int charlen = 0;
    for (int cp : input.codePoints().toArray()) {
      if (cp <= 0x7f) {
        charlen = 1;
      } else if (cp <= 0x7ff) {
        charlen = 2;
      } else if (cp <= 0xffff) {
        charlen = 3;
      } else if (cp < 0x1fffff) {
        charlen = 4;
      }
      if (resultlen + charlen > length) {
        break;
      }

      if (Character.isBmpCodePoint(cp)) {
        result.append((char) cp);
      } else {
        result.append(Character.highSurrogate(cp));
        result.append(Character.lowSurrogate(cp));
      }

      resultlen += charlen;
    }

    String temp = result.toString();

    if (stringLengthUTF8Bytes(temp) < length) {
      temp = Strings.padEnd(temp, temp.length() + length - stringLengthUTF8Bytes(temp), ' ');
    }

    return temp;
  }
}

package com.example.demo.exp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;

public class ExpUtils {

  private ExpUtils() {
    throw new IllegalStateException("Utility class");
  }

  private static final DateTimeFormatter FORMAT_PATTERN_DATE =
      DateTimeFormatter.ofPattern("uuuu-MM-dd");
  private static final DateTimeFormatter FORMAT_PATTERN_DATETIME =
      DateTimeFormatter.ofPattern("uuuu-MM-dd' 'HH:mm:ss");
  private static final DateTimeFormatter FORMAT_PATTERN_TIME =
      DateTimeFormatter.ofPattern("HH:mm:ss");
  private static Random rand = new Random();

  /**
   * 累加list的指定字段值
   *
   * <p>Date: 2018-08-20 18:12:52
   *
   * @author lizhipeng
   * @param lists
   * @param sumFieldName
   * @return
   */
  //  public static double sumList(List<Object> lists, String sumFieldName) {
  //    double result = 0;
  //
  //    for (Object list : lists) {
  //      result += nullsToZero(getFieldValueByName(list, sumFieldName));
  //    }
  //    return result;
  //  }

  /**
   * 返回当前时间，日期类型，格式"yyyy-MM-dd"，如 "2018-08-18"
   *
   * <p>Date: 2018-08-20 18:13:30
   *
   * @author lizhipeng
   * @return
   */
  public static String dateNow() {
    LocalDate date = LocalDate.now();
    return date.format(FORMAT_PATTERN_DATE);
  }

  /**
   * 返回当前时间，时间类型，格式'HH:mm:ss'，如 "15:23:01"
   *
   * <p>Date: 2018-08-20 18:14:02
   *
   * @author lizhipeng
   * @return
   */
  public static String timeNow() {
    LocalTime time = LocalTime.now();
    return time.format(FORMAT_PATTERN_TIME);
  }

  /**
   * 返回当前时间，时间日期类型，格式'yyyy-MM-dd HH:mm:ss'，如“2018-08-18 12:30:50”
   *
   * <p>Date: 2018-08-20 18:46:20
   *
   * @author lizhipeng
   * @return
   */
  public static String dateTimeNow() {
    LocalDateTime dateTime = LocalDateTime.now();
    return dateTime.format(FORMAT_PATTERN_DATETIME);
  }

  /**
   * 返回数值， 年份.
   *
   * <p>Date: 2018-08-20 18:59:32
   *
   * @author lizhipeng
   * @return
   */
  public static int year() {
    LocalDateTime dateTime = LocalDateTime.now();
    return dateTime.getYear();
  }

  /**
   * 返回数值， 1 (Monday) to 7 (Sunday).
   *
   * <p>Date: 2018-08-20 18:59:32
   *
   * @author lizhipeng
   * @return
   */
  public static int weekDay() {
    LocalDateTime dateTime = LocalDateTime.now();
    return dateTime.getDayOfWeek().getValue();
  }

  /**
   * 返回月份， 1 (January) to 12 (December).
   *
   * <p>Date: 2018-08-20 19:03:40
   *
   * @author lizhipeng
   * @return
   */
  public static int month() {
    LocalDateTime dateTime = LocalDateTime.now();
    return dateTime.getMonth().getValue();
  }

  /**
   * the day-of-month, from 1 to 31.
   *
   * <p>Date: 2018-08-20 19:19:44
   *
   * @author lizhipeng
   * @return
   */
  public static int dayOfMonth() {
    LocalDateTime dateTime = LocalDateTime.now();
    return dateTime.getDayOfMonth();
  }

  /**
   * 根据字段名返回字段值, 如果字段不存在或其他错误，返回null.
   *
   * <p>Date: 2018-08-20 09:12:52
   *
   * @author lizhipeng
   * @param obj
   * @param fieldName
   * @return
   */
  //  public static Object getFieldValueByName(Object obj, String fieldName) {
  //    try {
  //      Field field = null;
  //      field = obj.getClass().getDeclaredField(fieldName);
  //      field.setAccessible(true);
  //      return field.get(obj);
  //    } catch (NoSuchFieldException
  //        | IllegalArgumentException
  //        | IllegalAccessException
  //        | SecurityException e) {
  //      log.info("getFieldValueByName exception， obj:{}, fieldName:{}", obj, fieldName, e);
  //    }
  //    return null;
  //  }

  /**
   * 返回一个字段的数值，如果不存在，当成数字0处理
   *
   * <p>Date: 2018-08-20 18:47:11
   *
   * @author lizhipeng
   * @param obj
   * @return
   */
  public static double nullsToZero(Object obj) {
    if (obj == null) {
      return 0;
    }
    if (obj instanceof String) {
      return Double.parseDouble((String) obj);
    }

    // 其他类型，强制转化为double
    return (double) obj;
  }

  /**
   * Date: 2018-08-20 19:35:55
   *
   * @author lizhipeng
   * @param original
   * @param length
   * @return
   */
  public static String left(String original, int length) {
    return StringUtils.left(original, length);
  }

  /**
   * Date: 2018-08-20 19:51:15
   *
   * @author lizhipeng
   * @param original
   * @param length
   * @return
   */
  public static String right(String original, int length) {
    return StringUtils.right(original, length);
  }

  /**
   * 从begin到end截取字符串，序号从0开始(all position counting is zero-based)
   *
   * <p>Date: 2018-08-20 19:51:20
   *
   * @author lizhipeng
   * @param original
   * @param beginIndex
   * @param endIndex
   * @return
   */
  public static String javaMid(String original, int beginIndex, int endIndex) {
    return StringUtils.substring(original, beginIndex, endIndex);
  }

  /**
   * vb 的mid， 从begin开始截取字符串，序号从1开始，截取长度为length
   *
   * <p>Date: 2018-08-20 19:51:20
   *
   * @author lizhipeng
   * @param original
   * @param beginIndex
   * @param endIndex
   * @return
   */
  public static String mid(String original, int begin, int length) {
    return StringUtils.substring(original, begin - 1, begin + length - 1);
  }

  /**
   * 判断一个值是否在一个列表里
   *
   * <p>Date: 2018-08-20 20:59:02
   *
   * @author lizhipeng
   * @param test
   * @param args
   * @return
   */
  public static boolean in(Object test, Object... args) {
    List<Object> lists = Lists.newArrayList();
    for (int i = 0; i < args.length; i++) {
      lists.add(args[i]);
    }
    return lists.contains(test);
  }

  /**
   * 判断一个值是否在指定的区间内，区间可以是多个，最后一个可以是开区间，包含两边的边界值
   *
   * <p>Date: 2018-08-20 21:08:36
   *
   * @author lizhipeng
   * @param original
   * @param args
   * @return
   */
  public static boolean bt(Object test, Object... args) {

    if (test instanceof Number) {
      Number testNumber = (Number) test;
      for (int i = 0; i < args.length; i += 2) {
        if (i + 1 < args.length) {
          if (testNumber.doubleValue() >= ((Number) args[i]).doubleValue()
              && testNumber.doubleValue() <= ((Number) args[i + 1]).doubleValue()) {
            return true;
          }
        } else {
          if (testNumber.doubleValue() >= ((Number) args[i]).doubleValue()) {
            return true;
          }
        }
      }
      return false;
    }

    if (test instanceof Comparable) {
      @SuppressWarnings("unchecked")
      Comparable<Object> testTemp = (Comparable<Object>) test;
      for (int i = 0; i < args.length; i += 2) {
        if (i + 1 < args.length) {
          if (testTemp.compareTo(args[i]) >= 0 && testTemp.compareTo(args[i + 1]) <= 0) {
            return true;
          }
        } else {
          if (testTemp.compareTo(args[i]) >= 0) {
            return true;
          }
        }
      }
      return false;
    }

    throw new ExpException(
        String.format("表达式异常，不支持的BT操作，参数值%s,%s", test, StringUtils.join(args, ",")));
  }

  /**
   * 生成[0-i)之间的一个随机整数
   *
   * @param i
   * @return
   */
  public static int rand(int i) {
    return rand.nextInt(i);
  }
  
  /**
   * 生成 [0-1)之间的一个随机小数
   *
   * @param i
   * @return
   */
  public static double random() {
    return rand.nextInt(100000)/100000.0;
  }
}

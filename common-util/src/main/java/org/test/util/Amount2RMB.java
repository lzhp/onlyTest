package org.test.util;

import java.math.BigDecimal;
import java.util.Map;
import org.junit.Assert;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Amount2RMB {
  /** 汉语中数字大写 */
  private static final String[] CN_UPPER_NUMBER = {
    "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"
  };
  /** 汉语中货币单位大写，这样的设计类似于占位符 */
  private static final String[] CN_UPPER_MONETRAY_UNIT = {
    "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾", "佰", "仟"
  };
  /** 特殊字符：整 */
  private static final String CN_FULL = "整";
  /** 特殊字符：负 */
  private static final String CN_NEGATIVE = "负";
  /** 金额的精度，默认值为2 */
  private static final int MONEY_PRECISION = 2;
  /** 特殊字符：零元整 */
  private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

  /**
   * 把输入的金额转换为汉语中人民币的大写
   *
   * @param numberOfMoney 输入的金额, 支持两位小数，超过两位时会四舍五入
   * @return 对应的汉语大写
   */
  public static String toChinese(BigDecimal numberOfMoney) {

    // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
    // positive.
    int signum = numberOfMoney.signum();
    // 零元整的情况
    if (signum == 0) {
      return CN_ZEOR_FULL;
    }
    // 这里会进行金额的四舍五入
    long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
    // 得到小数点后两位值
    long scale = number % 100;
    int numUnit = 0;
    int numIndex = 0;
    StringBuilder sb = new StringBuilder();
    boolean getZero = false;
    // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
    if (!(scale > 0)) {
      numIndex = 2;
      number = number / 100;
      getZero = true;
    }
    if ((scale > 0) && (!(scale % 10 > 0))) {
      numIndex = 1;
      number = number / 10;
      getZero = true;
    }
    int zeroSize = 0;
    while (true) {
      if (number <= 0) {
        break;
      }
      // 每次获取到最后一个数
      numUnit = (int) (number % 10);
      if (numUnit > 0) {
        if ((numIndex == 9) && (zeroSize >= 3)) {
          sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
        }
        if ((numIndex == 13) && (zeroSize >= 3)) {
          sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
        }
        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
        sb.insert(0, CN_UPPER_NUMBER[numUnit]);
        getZero = false;
        zeroSize = 0;
      } else {
        ++zeroSize;
        if (!(getZero)) {
          sb.insert(0, CN_UPPER_NUMBER[numUnit]);
        }
        if (numIndex == 2) {
          if (number > 0) {
            sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
          }
        } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
          sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
        }
        getZero = true;
      }
      // 让number每次都去掉最后一个数
      number = number / 10;
      ++numIndex;
    }
    // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
    if (signum == -1) {
      sb.insert(0, CN_NEGATIVE);
    }
    // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
    if (!(scale > 0)) {
      sb.append(CN_FULL);
    }
    return sb.toString();
  }

  public static String toChinese(String money) {

    String temp = "0";
    if (money != null) {
      temp = money.replace(",", "");
    }
    return toChinese(new BigDecimal(temp));
  }

  public static String toChinese(double money) {

    return toChinese(BigDecimal.valueOf(money));
  }

  public static void main(String[] args) {

    log.info(toChinese(10000));

    Map<String, String> data = Maps.newHashMap();
    data.put("16409.02", "壹万陆仟肆佰零玖元零贰分");
    data.put("16400.02", "壹万陆仟肆佰元零贰分");
    data.put("100000006400.00", "壹仟亿零陆仟肆佰元整");
    data.put("1,409.50", "壹仟肆佰零玖元伍角");
    data.put("6,007.14", "陆仟零柒元壹角肆分");
    //data.put("1,680.32", "壹仟陆佰捌拾元叁角贰分");
    data.put("325.04", "叁佰贰拾伍元零肆分");
    data.put("4,321.00", "肆仟叁佰贰拾壹元整");
    data.put("0.01", "壹分");
    data.put("0.00", "零元整");
    data.put("1234,5678,9012.34", "壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元叁角肆分");
    //data.put("1000,1000,1000.10", "壹仟亿零壹仟万零壹仟元壹角");
    data.put("9009,9009,9009.99", "玖仟零玖亿玖仟零玖万玖仟零玖元玖角玖分");
    data.put("5432,0001,0001.01", "伍仟肆佰叁拾贰亿零壹万零壹元零壹分");
    data.put("1000,0000,1110.00", "壹仟亿零壹仟壹佰壹拾元整");
    data.put("1010,0000,0001.11", "壹仟零壹拾亿零壹元壹角壹分");
    data.put("1000,0000,0000.01", "壹仟亿元零壹分");

    for (Map.Entry<String, String> entry : data.entrySet()) {
      Assert.assertEquals("异常", entry.getValue(), toChinese(entry.getKey()));
    }
  }
}

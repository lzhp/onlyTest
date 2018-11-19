/**
 * Date: 2018-05-01 00:51:08.
 */
package org.test.exception;

/**
 * Date: 2018-05-01 00:51:08.
 * 
 * @author: lizhipeng.
 * @description:
 */
public class BusinessException extends BaseException {

  /**
   * serialVersionUID:.
   */
  private static final long serialVersionUID = 4773191322243855500L;

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   */
  public <T extends Enum<T> & ErrorEnumInterface> BusinessException(T errorEnum) {
    this(errorEnum, "");
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   * @param bussinessId 关键业务单证编号，如报关单号
   */
  public <T extends Enum<T> & ErrorEnumInterface> BusinessException(T errorEnum,
      String bussinessId) {
    this(errorEnum, bussinessId, null);
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   * @param bussinessId 关键业务单证编号，如报关单号
   * @param cause 底层错误
   */
  public <T extends Enum<T> & ErrorEnumInterface> BusinessException(T errorEnum, String bussinessId,
      Throwable cause) {
    super(errorEnum, bussinessId, cause);
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 错误枚举
   * @param bussinessId 关键单证编号
   * @param message 错误描述
   * @param cause 底层错误
   */
  public <T extends Enum<T> & ErrorEnumInterface> BusinessException(T errorEnum, String bussinessId,
      String message, Throwable cause) {
    super(errorEnum, bussinessId, message, cause);
  }

}


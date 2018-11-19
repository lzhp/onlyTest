package org.test.exception;

public class CommonException extends BusinessException {

  /**
   * 
   */
  private static final long serialVersionUID = 6702493395432642232L;

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   */
  public <T extends Enum<T> & ErrorEnumInterface> CommonException(T errorEnum) {
    this(errorEnum, "");
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   * @param bussinessId 关键业务单证编号，如报关单号
   */
  public <T extends Enum<T> & ErrorEnumInterface> CommonException(T errorEnum, String bussinessId) {
    this(errorEnum, bussinessId, null);
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   * @param bussinessId 关键业务单证编号，如报关单号
   * @param cause 底层错误
   */
  public <T extends Enum<T> & ErrorEnumInterface> CommonException(T errorEnum, String bussinessId,
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
  public <T extends Enum<T> & ErrorEnumInterface> CommonException(T errorEnum, String bussinessId,
      String message, Throwable cause) {
    super(errorEnum, bussinessId, message, cause);
  }

}

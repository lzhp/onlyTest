/**
 * Date: 2018-04-30 21:28:53.
 */
package org.test.exception;

import com.google.common.base.CaseFormat;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

/**
 * Date: 2018-04-30 21:28:53.
 * 
 * @author: lizhipeng.
 * @description:
 */
@Getter
public abstract class BaseException extends RuntimeException {

  /**
   * serialVersionUID:.
   */
  private static final long serialVersionUID = 4773191322243855500L;

  /**
   * 错误编号
   */
  private final String code;

  /**
   * 错误编号，英文
   */
  private final String codeEn;

  /**
   * 关键业务单证代码
   */
  private final String bussinessId;

  /**
   * 附加错误信息
   */
  @Setter
  private String addtionalMessage;


  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   */
  public <T extends Enum<T> & ErrorEnumInterface> BaseException(T errorEnum) {
    this(errorEnum, "");
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   * @param businessId 关键业务单证编号，如报关单号
   */
  public <T extends Enum<T> & ErrorEnumInterface> BaseException(T errorEnum, String businessId) {
    this(errorEnum, businessId, null);
  }

  /**
   * 业务异常
   * 
   * @param errorEnum 业务异常枚举值
   * @param businessId 关键业务单证编号，如报关单号
   * @param cause 底层错误
   */
  public <T extends Enum<T> & ErrorEnumInterface> BaseException(T errorEnum, String businessId,
      Throwable cause) {
    this(errorEnum, businessId, "", cause);
  }

  /**
   * 异常
   * 
   * @param errorEnum 业务异常枚举
   * @param businessId 关键业务单证编号
   * @param message 错误信息描述
   * @param cause 底层错误
   */
  public <T extends Enum<T> & ErrorEnumInterface> BaseException(T errorEnum, String businessId,
      String message, Throwable cause) {
    super(errorEnum.getErrorInfo().getMessage()
        + (Strings.isNullOrEmpty(message) ? "" : "," + message)
        + (cause == null ? "" : ("[" + cause.getMessage()) + "]"), cause);
    ErrorInfo errorInfo = errorEnum.getErrorInfo();
    this.code = errorInfo.getCode();
    if (!Strings.isNullOrEmpty(businessId)) {
      this.bussinessId = businessId;
    } else {
      if (cause instanceof BaseException) {
        this.bussinessId = ((BaseException) cause).getBussinessId();
      } else {
        this.bussinessId = "";
      }
    }
    this.codeEn = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, errorEnum.name());
  }



  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("message", this.getMessage())
        .add("businessId", this.getBussinessId()).add("code", this.getCode())
        .add("codeEn", this.getCodeEn()).add("addtionalMessage", this.getAddtionalMessage())
        .toString();
  }
}


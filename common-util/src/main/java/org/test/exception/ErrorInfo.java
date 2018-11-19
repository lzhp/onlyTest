/**
 * Date: 2018-04-30 21:35:20.
 */
package org.test.exception;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Date: 2018-04-30 21:35:20.
 * 
 * @author: lizhipeng.
 * @description:
 */
@Getter
@ToString
@Builder
public class ErrorInfo implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = 7878068469202292871L;

  /**
   * 错误编号
   */
  private String code;

  /**
   * 错误信息
   */
  private String message;

  /**
   * 错误信息
   * 
   * @param message 错误简单描述
   * @param errorCode 错误编号
   */
  public ErrorInfo(String errorCode, String message) {
    this.message = message;
    this.code = errorCode;
  }
}


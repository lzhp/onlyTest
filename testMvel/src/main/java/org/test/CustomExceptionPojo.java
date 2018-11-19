/**
 * Date: 2018-03-26 21:50:49.
 * 
 * @author: lizhipeng.
 */
package org.test;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-03-26 21:50:49.
 * 
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@AllArgsConstructor // need for builder
@NoArgsConstructor // need for jpa
@JsonTypeName("exception")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class CustomExceptionPojo {

  /**
   * 错误号
   */
  private String code;

  /**
   * 关键业务单证号码
   */
  private String bussinessId;

  /**
   * 简要错误信息
   */
  private String message;

  /**
   * 详细错误信息(视情况,一般不返回客户端)
   */
  private String detail;



}


/**  
 * Date: 2018-08-22 00:43:08. 
 */  
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
 * Date: 2018-08-22 00:43:08. 
 * @author: lizhipeng.
 * @description: 
 */  
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryWorkflow implements Serializable {
  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = 5378359530009868543L;
  private String entryId;
  private String stepCode;
  private LocalDateTime createDate;
  private String stepStatus;
  private String procEr;
  private String procResult;
  private LocalDateTime overDate;
  private String stepRemark;
  private String certType;
  private String certId;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;

}
  

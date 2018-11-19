/**  
 * Date: 2018-08-22 00:45:08. 
 */  
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
 * Date: 2018-08-22 00:45:08. 
 * @author: lizhipeng.
 * @description: 
 */  
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryCertRel implements Serializable {
 
  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -3154249263729117851L;
  private String entryId;
  private String gNo;
  private String certType;
  private String certCode;
  private String certGNo;
  private Double certQty;
  private String kvExtension;
  private Double certPrice;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;


}
  

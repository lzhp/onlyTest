/**  
 * Date: 2018-08-22 00:41:09. 
 */  
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
 * Date: 2018-08-22 00:41:09. 
 * @author: lizhipeng.
 * @description: 
 */  
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryFees implements Serializable {
  
  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = 3035619988573400550L;
  private String entryId;
  private String gNo;
  private String feeItemNo;
  private String feeCategory;
  private String feeMark;
  private Double feeRate;
  private String feeCurrency;
  private Double feePrice;
  private Double feePriceTotal;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;

}
  

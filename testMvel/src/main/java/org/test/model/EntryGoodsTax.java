/** Date: 2018-08-22 00:40:00. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 00:40:00.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryGoodsTax implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -3829982503871531526L;
  private String entryId;
  private String gNo;
  private String taxNo;
  private String taxCategory;
  private String delayMark;
  private String dutyFlag;
  private String taxType;
  private Double realTax;
  private Double taxCut;
  private Double taxRate;
  private Double tRate;
  private Double qtyTaxRate;
  private Double qtyTRate;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

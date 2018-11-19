/** Date: 2018-08-22 01:02:04. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 01:02:04.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinList implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -7651840711194008881L;
  private String entryId;
  private String gNo;
  private String iqGLicCategory;
  private String iqGLicId;
  private String vinId;
  private String vinQualityWarranty;
  private String vinModel;
  private LocalDateTime vinBillDate;
  private String vinGoodsNameCn;
  private String vinGoodsNameEn;
  private Double vinInvoiceAmount;
  private String vinMotorId;
  private String vinFrameNo;
  private String vinPrice;
  private String vinSeq;
  private String vinInvoiceId;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

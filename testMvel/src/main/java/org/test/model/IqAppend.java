/** Date: 2018-08-22 01:01:13. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 01:01:13.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IqAppend implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -7000506689943388438L;
  private String entryId;
  private String gNo;
  private String iqGLicCategory;
  private String iqGLicId;
  private String iqGLicDeductNo;
  private Double iqGLicDeductQty;
  private String iqGLicName;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

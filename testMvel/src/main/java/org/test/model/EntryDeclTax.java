/** Date: 2018-08-22 00:38:32. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 00:38:32.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryDeclTax implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = 1650747550687549704L;
  private String entryId;
  private String gNo;
  private String taxNo;
  private String taxCategory;
  private Double realTax;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

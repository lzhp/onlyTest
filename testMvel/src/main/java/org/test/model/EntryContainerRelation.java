/** Date: 2018-08-22 00:36:16. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 00:36:16.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryContainerRelation implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -2095069504362840741L;
  private String entryId;
  private String contaId;
  private String gNo;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

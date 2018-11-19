/** Date: 2018-08-22 01:00:05. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 01:00:05.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryCert implements Serializable {

  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -4577126236966428109L;
  private String entryId;
  private String certType;
  private String certCode;
  private String kvExtension;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

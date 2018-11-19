/** Date: 2018-08-22 00:43:58. */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-08-22 00:43:58.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryDocu implements Serializable {
  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = 8108078514655502606L;
  private String entryId;
  private String docuType;
  private String docuCode;
  private String docuName;
  private String docuSource;
  private String docuFileId;
  private String mimeType;
  private String formatType;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;
}

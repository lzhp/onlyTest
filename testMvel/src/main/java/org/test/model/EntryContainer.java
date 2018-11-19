/**  
 * Date: 2018-08-22 00:34:49. 
 */  
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
 * Date: 2018-08-22 00:34:49. 
 * @author: lizhipeng.
 * @description: 
 */  
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryContainer implements Serializable {
  
  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -1807859682585089207L;
  private String entryId;
  private String contaId;
  private String contaModel;
  private String contaGNo;
  private Double grossWt;
  private String contaStatusCode;
  private String contaSplitFlag;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;


}
  

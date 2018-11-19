/**  
 * Date: 2018-08-22 00:42:00. 
 */  
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**  
 * Date: 2018-08-22 00:42:00. 
 * @author: lizhipeng.
 * @description: 
 */  
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryParty implements Serializable {
  /**  
   * serialVersionUID:.   
   */
  private static final long serialVersionUID = -8760080094754444193L;
  
  
  private String entryId;
  private String partyType;
  private String partyCode;
  private String partyName;
  private String partyNameEn;
  private String partyAddress;
  private String unionCode;
  private String contactEr;
  private String contactPhone;
  private String contactAddress;
  private String coClass;
  private String declareEr;
  private String declareName;
  private String staticsType;
  private String adminDivisionCode;
  private String ecoAreaCode;
  private String ecoCategoryCode;
  private String businessCategoryBrief;
  private String specialAreaNo;
  private String ownershipAreaNo;
  private String partyIqId;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;

}
  

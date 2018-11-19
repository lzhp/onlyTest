/**
 * Date: 2018-03-21 21:11:35.
 *
 * @author: lizhipeng.
 */
package org.test.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Date: 2018-03-21 21:11:35.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryList implements Serializable {

  /** serialVersionUID:. */
  private static final long serialVersionUID = -2615238933863779110L;

  private String entryId;
  @Setter(onMethod_ = {@JsonSetter("GNo")})
  @Getter(onMethod_ = {@JsonGetter("GNo")})
  private String gNo;
  private String codeTs;
  private String iqCode;

  @Setter(onMethod_ = {@JsonSetter("GName")})
  @Getter(onMethod_ = {@JsonGetter("GName")})
  private String gName;
  private String iqGName;
  private String gNameFn;
  private String gModel;
  private String iqGModel;
  private String gComposition;
  private Long prodWarranty;
  private String prodValidaty;
  private String dutyMode;
  private String gtin;
  private Double gQty;
  private String gUnit;
  private Double qty1;
  private String unit1;
  private Double qty2;
  private String unit2;
  private Double declPrice;
  private Double declTotal;
  private String tradeCurr;
  private Double customsValue;
  private Double dutyValue;
  private Double rmbPrice;
  private Double usdPrice;
  private Double exchangeRate;
  private String origCountry;
  private String origRegion;
  private String destCountry;
  private String destDistrictCode;
  private String iqDestDistrictCode;
  private String suppDistrictCode;
  private String iqSuppDistrictCode;
  private String fgnProducerName;
  private String productModel;
  private String productName;
  private String productDate;
  private String productLog;
  private String ungid;
  private String ungFlag;
  private String ungModel;
  private String ungClassify;
  private String ungGName;
  private String productCharCode;
  private String useTo;
  private String iqDocCode;
  private String gCertFlag;
  private String kvExtension;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;

}

/**
 * Date: 2018-03-21 21:11:23.
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
 * Date: 2018-03-21 21:11:23.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryHead implements Serializable {

  /** serialVersionUID:. */
  private static final long serialVersionUID = 8157634701876876560L;

  private String entryId;
  private String preEntryId;
  private String promiseType;
  private String entryType;
  @Setter(onMethod_ = { @JsonSetter("IEFlag") })
  @Getter(onMethod_ = { @JsonGetter("IEFlag") })  
  private String iEFlag;
  private String declPort;
  private String iqDeclPort;
  private String imPort;
  private String exPort;
  @Setter(onMethod_ = { @JsonSetter("IqIEPort") })
  @Getter(onMethod_ = { @JsonGetter("IqIEPort") }) 
  private String iqIEPort;
  private LocalDateTime imDate;
  private LocalDateTime exDate;
  private LocalDateTime dDate;
  private String tradeMode;
  private String cutMode;
  private String consignorScc;
  private String consignorCode;
  private String consigneeScc;
  private String consigneeCode;
  private String consignorName;
  private String consigneeName;
  private String frnConsignorCode;
  private String frnConsignorName;
  private String frnConsigneeCode;
  private String frnConsigneeName;
  private String consumerCodeScc;
  private String consumerCode;
  private String consumerName;
  private String producerCodeScc;
  private String producerCode;
  private String producerName;
  private String agentCodeScc;
  private String agentCode;
  private String agentName;
  private String tradeCountry;
  private String trafMode;
  private String trafName;
  private String voyageNo;
  private String billNo;
  private String blNo;
  private String manifestId;
  private String departureCountry;
  private String arrivedCountry;
  private String departurePort;
  private String lastStopPort;
  private String destinationPort;
  private String entryPort;
  private String warehouseLocation;
  private Double containerNum;
  private String isOrigContainer;
  private String wrapType;
  private String subWrapType;
  private Double packNo;
  private Double grossWt;
  private Double netWt;
  private String transMode;
  private String relId;
  private String relativeIqId;
  private String relReason;
  private String maskId;
  private String noteS;
  private String consignorIqId;
  private String consigneeIqId;
  private String consumerIqId;
  private String producerIqId;
  private String agentIqId;
  private String kvExtension;
  private LocalDateTime recCreateTime;
  private LocalDateTime recLastUpdateTime;
  private Long recVersion;
  private String archVer;

}

/**
 * Date: 2018-03-30 15:57:05.
 *
 * @author: lizhipeng.
 */
package org.test.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Date: 2018-03-30 15:57:05.
 *
 * @author: lizhipeng.
 * @description:
 */
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entry implements Serializable {
  /** serialVersionUID:. */
  private static final long serialVersionUID = 7343823759560904276L;

  private EntryHead entryHead;
  private List<EntryList> entryList;
  private List<EntryCert> entryCert;
  private List<EntryCertRel> entryCertRel;
  private List<EntryContainer> entryContainer;
  private List<EntryContainerRelation> entryContainerRelation;
  private List<EntryDeclTax> entryDeclTax;
  private List<EntryDocu> entryDocu;
  private List<EntryFees> entryFees;
  private List<EntryGoodsTax> entryGoodsTax;
  private List<EntryParty> entryParty;
  private List<EntryWorkflow> entryWorkflow;
  private List<IqAppend> iqAppend;
  private List<VinList> vinList;

  /**
   * 
   *
   * Date: 2018-08-21 16:42:16
   * 
   * @author lizhipeng
   *
   * @return
   */
  public double listSumTradeTotal() {
    double result = 0;
    for (EntryList list : entryList) {
      result += list.getCustomsValue();
    }
    return result;
  }
}

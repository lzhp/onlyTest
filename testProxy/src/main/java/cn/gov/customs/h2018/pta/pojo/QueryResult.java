package cn.gov.customs.h2018.pta.pojo;

import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryResult {
  
  private String code;
  private String errorDesc;
  private String queryId;
  private String status;
  private RegionList result;

  
  @Data
  @ToString
  public class RegionList{
    private List<String> countries;
    private List<String> cities;
  }
}

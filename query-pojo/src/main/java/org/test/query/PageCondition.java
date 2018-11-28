package org.test.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageCondition {

  /**
   * 当前页码，0为第一页
   */
  int number;
  /**
   * 每页的的大小
   */
  @Builder.Default
  int size = 50;

}

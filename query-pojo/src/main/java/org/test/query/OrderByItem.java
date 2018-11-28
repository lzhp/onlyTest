package org.test.query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderByItem {

  public enum Direction {
    ASC, DESC
  }

  private String property;
  @Builder.Default
  private Direction direction = Direction.ASC;

  public String toSqlString() {
    Validate.isTrue(!StringUtils.isBlank(property), "字段名为空");

    return String.format("%s %s", property, direction);
  }
}

package org.test.query;

import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class ConditionItem {

  @Getter
  public enum Operator {

    /** CUSTOM property里保存的是自定义语句 */
    EQ(" = "),
    LE(" <= "),
    GE(" >= "),
    GT(" > "),
    LT(" < "),
    NOTEQ(" != "),
    LIKE(" LIKE "),
    BT(" BETWEEN "),
    ISNULL(" IS NULL "),
    NOTNULL(" IS NOT NULL "),
    IN(" IN "),
    NOTIN(" NOT IN "),
    CUSTOM("CUSTOM");

    private String oper;

    Operator(String oper) {
      this.oper = oper;
    }
  }

  /**
   * 需要判断的字段，如 aaa = 'value'等，
   *
   * <p>单个参数放到values[0]中，
   *
   * <p>in bt等函数用的参数较多，参数放到values中
   */
  private String property;

  private Operator operator;
  private Object[] values;

  public ConditionItem(String property, Operator operator, Object value) {
    this.property = property;
    this.operator = operator;
    this.values = ArrayUtils.toArray(value);
  }

  @SuppressWarnings("rawtypes")
  public void setValues(Object... values) {
    if (values.length == 1 && values[0] instanceof Collection) {
      this.values = ((Collection) values[0]).toArray();
    } else if (values.length == 1 && values[0] instanceof Object[]) {
      this.values = (Object[]) values[0];
    } else {
      this.values = values;
    }
  }

  public String toSqlString() {

    Validate.isTrue(this.isValid(), "变量未初始化:{%s}", this);

    String result = "";
    switch (operator) {
      case EQ:
      case LE:
      case GE:
      case GT:
      case LT:
      case NOTEQ:
      case LIKE:
        {
          result = property + operator.getOper() + "?";
          break;
        }
      case ISNULL:
      case NOTNULL:
        {
          result = property + operator.getOper();
          break;
        }
      case IN:
      case NOTIN:
        {
          Validate.isTrue(ArrayUtils.isNotEmpty(this.values), "values 未初始化！");
          String[] t = new String[this.values.length];
          Arrays.fill(t, "?");
          result = property + operator.getOper() + "(" + StringUtils.join(t, ',') + ")";
          break;
        }

      case BT:
        {
          Validate.isTrue(
              ArrayUtils.isNotEmpty(this.values),
              "values 未初始化！values={%s}",
              ArrayUtils.toString(this.values, "null"));
          Validate.isTrue(
              this.values.length >= 2,
              "values 至少有两个元素！values={%s}",
              ArrayUtils.toString(this.values));
          result = property + operator.getOper() + " ? and ? ";
          break;
        }
      case CUSTOM:
        {
          result = property;
          break;
        }
      default:
        {
          log.warn("unexpect operator:{}", operator);
        }
    }
    return result;
  }

  public Object[] toParameterArray() {
    Validate.isTrue(this.isValid(), "变量未初始化");

    Object[] result = ArrayUtils.EMPTY_OBJECT_ARRAY;
    switch (operator) {
      case EQ:
      case LE:
      case GE:
      case GT:
      case LT:
      case NOTEQ:
      case LIKE:
        {
          result = ArrayUtils.subarray(values, 0, 1);
          break;
        }
      case ISNULL:
      case NOTNULL:
        {
          result = null;
          break;
        }
      case IN:
      case NOTIN:
      case CUSTOM:
        {
          result = values;
          break;
        }
      case BT:
        {
          result = ArrayUtils.subarray(values, 0, 2);
          break;
        }
      default:
        {
          log.warn("unexpect operator:{}", operator);
        }
    }
    return result;
  }

  private boolean isValid() {
    if (StringUtils.isEmpty(property) || this.operator == null) {
      return false;
    }
    return true;
  }

  public static ConditionItem eq(String name, Object value) {
    return new ConditionItem(name, Operator.EQ, value);
  }

  public static ConditionItem le(String name, Object value) {
    return new ConditionItem(name, Operator.LE, value);
  }

  public static ConditionItem ge(String name, Object value) {
    return new ConditionItem(name, Operator.GE, value);
  }

  public static ConditionItem like(String name, Object value) {
    return new ConditionItem(name, Operator.LIKE, value);
  }

  public static ConditionItem isNull(String name) {
    return new ConditionItem(name, Operator.ISNULL, "");
  }

  public static ConditionItem notNull(String name) {
    return new ConditionItem(name, Operator.NOTNULL, "");
  }

  public static ConditionItem gt(String name, Object value) {
    return new ConditionItem(name, Operator.GT, value);
  }

  public static ConditionItem lt(String name, Object value) {
    return new ConditionItem(name, Operator.LT, value);
  }

  public static ConditionItem notEq(String name, Object value) {
    return new ConditionItem(name, Operator.NOTEQ, value);
  }

  public static ConditionItem in(String name, Object[] values) {
    return new ConditionItem(name, Operator.IN, values);
  }

  public static ConditionItem notIn(String name, Object[] values) {
    return new ConditionItem(name, Operator.NOTIN, values);
  }

  public static ConditionItem bt(String name, Object[] values) {
    return new ConditionItem(name, Operator.BT, values);
  }
}

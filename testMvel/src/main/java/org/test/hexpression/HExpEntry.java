package org.test.hexpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Map;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.test.model.Entry;
import com.google.common.collect.Maps;

public class HExpEntry {
  
  public static boolean check(String rule, Entry entry) {
    return check(rule, entry, 0);    
  }
  
  public static boolean check(String rule, Entry entry, int goodsIndex) {
    String entryFunc = readFromFile();
    VariableResolverFactory functionFactory = new MapVariableResolverFactory();
    MVEL.eval(entryFunc, functionFactory);

    VariableResolverFactory myVarFactory = new MapVariableResolverFactory();
    myVarFactory.setNextFactory(functionFactory);
    
    Map<String, Object> vars = Maps.newHashMap();
    vars.put("MVEL", MVEL.class);
    vars.put("entry", Entry.class);
    Serializable s = MVEL.compileExpression(rule, vars);
    
    entry.setGoodsIndex(goodsIndex);
    return MVEL.executeExpression(s, entry, myVarFactory, boolean.class);
  }

  /**
   * desc：报关单的全局函数，从文件里读入
   *
   * @return
   */
  private static String readFromFile() {
    final String fileName = "entry.mvel";
    Resource resource = new ClassPathResource(fileName);
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

      // 每次读取文件的缓存
      String temp = null;
      StringBuilder data = new StringBuilder();
      while ((temp = reader.readLine()) != null) {
        data.append(temp);
      }
      return data.toString();
    } catch (IOException e) {
      throw new HExpException("读取文件出错：" + fileName);
    }
  }
}

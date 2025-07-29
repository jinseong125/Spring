package org.shark.ioc.chap01_xml;

import java.util.Map;

public class Divider {
  
  public Map<String, Integer> divide(int a, int b) {
    return Map.of("quotient", a / b, "remainder", a % b);
  }

}

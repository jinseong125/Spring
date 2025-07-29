package org.shark.ioc.chap02_java;

import java.util.Map;

public class Divider {
  
  public Map<String, Integer> divide(int a, int b) {
    return Map.of("quotient", a / b, "remainder", a % b);
  }

}

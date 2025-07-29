package org.shark.ioc.chap02_java;

public class Adder {
  
  public int add(int... args) {
    if (args == null) {
      return 0;
    }
    int total = 0;
    for (int i = 0; i < args.length; i++) {
      total += args[i];
    }
    return total;
  }

}

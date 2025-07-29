package org.shark.ioc.chap03_component;

public class Subtractor {
  
  public int subtract(int a, int b) {
    return (a >= b) ? a - b : b - a;
  }

}

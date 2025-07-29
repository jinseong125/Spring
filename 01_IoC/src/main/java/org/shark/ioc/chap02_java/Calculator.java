package org.shark.ioc.chap02_java;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor   //----- Calculator() : 디폴트 생성자
@AllArgsConstructor  //----- Calculator(String, int, Adder, Subtractor, Multiplier multiplier, Divider divider)
@Getter
@Setter
@ToString
public class Calculator {
  private String brand; 
  private int price;
  private Adder adder;
  private Subtractor subtractor;
  private Multiplier multiplier;
  private Divider divider;
}

package org.shark.ioc.chap03_component;

import org.springframework.stereotype.Component;

import lombok.Getter;

/*
 * @Component
 * 
 * 1. 클래스 레벨 어노테이션으로, 해당 클래스를 Spring Container에 빈(bean)으로 등록하는 역할을 합니다.
 * 2. 스프링 컴포넌트 스캔(Component Scan) 기능이 활성화 되어 있어야 합니다. (servlet-context.xml 참조)
 * 3. XML의 <bean> 태그나 Configuration 파일의 @Bean 어노테이션을 이용해 수동으로 등록하던 방식을 자동 등록으로 보완한 방식입니다.
 * 4. 빈(bean)의 이름은 클래스 이름을 camelCase로 바꾼 형태가 됩니다.
 *    @Component(value = "beanId") 형식으로 빈의 이름을 지정할 수 있습니다.
 * 5. 구체적 스테레오 타입의 어노테이션들의 부모입니다. (특정 목적을 가진 어노테이션 : @Controller, @Service, @Repository)
 */

@Component
@Getter
public class Calculator {
  private String brand = "삼성";
  private int price = 20000;
  private Adder adder = new Adder();
  private Subtractor subtractor = new Subtractor();
  private Multiplier multiplier = new Multiplier();
  private Divider divider = new Divider();
}

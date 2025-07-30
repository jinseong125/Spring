package org.shark.di;

import org.shark.di.service.DIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * @Controller
 * 
 * 1. 요청(Request)을 처리하는 컨트롤러 클래스임을 나타내는 어노테이션입니다.
 * 2. 클라이언트의 HTTP 요청을 받아 적절한 서비스 메소드를 호출하고, 처리결과를 뷰(view)로 전달합니다.
 * 3. @Component의 특수화 된 어노테이션으로 Spring Container에 자동으로 빈으로 등록됩니다.
 * 4. 요총 매핑 처리를 위해서 @RequestMapping, @GetMapping, @PostMapping 등의 어노테이션을 함께 사용합니다.
 */

/*
 * @Autowired
 * 
 * 1. 스프링에서 의존성 주입(Dependency Injection, DI)을 간편하게 처리하기 위해 사용되는 어노테이션입니다.
 * 2. Spring Container에 등록된 빈(Bean) 중에서 "타입에 맞는 객체"를 자동으로 찾아서 주입합니다.
 * 3. required 속성 : 디폴트 true. 주입할 빈(Bean)이 없으면 애플리케이션이 동작하지 않습니다. required=false로 설정하면 예외없이 실행됩니다.
 * 4. 타입 기준 검색
 *    1) 우선 타입을 기준으로 해당 빈을 찾습니다.
 *    2) 해당 타입의 빈이 1개이면 해당 빈을 주입합니다.
 *    3) 해당 타입의 빈이 2개 이상이면 예외를 발생합니다.
 *        (1) 필드 이름과 동일한 이름의 빈이 있으면 해당 빈을 주입합니다. (필드 주입)
 *        (2) 파라미터 이름과 동일한 이름의 빈이 있으면 해당 빈을 주입합니다.(수정자, 생성자 주입)
 *        (3) 이름도 일치하는 빈이 없으면 예외가 발생합니다.
 *        (4) 이런 문제를 피하기 위해서 @Qualifier, @Primary를 명시하기도 합니다.
 * 5. 스프링에서 인터페이스 타입의 빈을 주입할 때는 "인터페이스 타입"으로 선언하는 것이 원칙이며 권장 사항입니다.
 * 6. 의존성 주입 대상
 *    1) Field       (필드) ----------> 코드 간단. 불변성 약화(final 불가)
 *    2) Setter      (수정자) --------> 빈이 없어도 동작 가능(런타임 오류 발생). 불변성 악화(final 불가)
 *    3) Constructor (생성자) --------> 스프링에서 권장하는 방식. 불변성 보장(final 가능)
 */

@Controller
public class MainController {
  
  //----- DI 주입 연습
  
  //----- 1. 필드 주입
  // @Aurtowired
  // private DIService diService;
  
  //----- 2. 수정자 주입
  // private DIService diService;
  //
  // @Autowired public void setDiService(DIService diService) { //----- 파라미터  DIService diService로 주입받습니다. 
  // this.diService = diService; 
  // }

  //----- 3. 생성자 주입
  private DIService diService;
  
  @Autowired  //---------------------------------------- 생성자가 한 개인 경우 생략할 수 있습니다.
  public MainController(DIService diService) {  //----- 파라미터  DIService diService로 주입받습니다. 
    super();
    this.diService = diService;
  }

  @RequestMapping(value = "/")
  public String main() {
    diService.serviceMethod();
    return "main";
  }
  
}

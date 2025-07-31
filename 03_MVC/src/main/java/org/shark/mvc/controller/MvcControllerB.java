package org.shark.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.shark.mvc.model.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/b")
@Controller
public class MvcControllerB {

  //----- 요청 주소 : /b/list?page=1&sort=DESC
  @RequestMapping("/list")
  public String methodA(HttpServletRequest request) {  //----- 요청 파라미터 처리를 위한 HttpServletRequest request 선언이 가능합니다.
    
    //----- sort 전달이 없거나, DESC 또는 ASC가 아니면 "DESC" 값을 사용합니다.
    String sort = request.getParameter("sort");
    if (sort == null || !(sort.equalsIgnoreCase("ASC") || sort.equalsIgnoreCase("DESC"))) {
      sort = "DESC";
    }
    
    //----- page 값이 정수가 아닌 경우(전달이 안 된 경우도 포함) "1" 값을 사용합니다.
    int page = 1;
    try {
      page = Integer.parseInt(request.getParameter("page"));
    } catch (Exception e) {
      page = 1;
    }
    
    //----- 결과 확인
    System.out.println("page : " + page);
    System.out.println("sort : " + sort);
    
    return "b/list";
    
  }
  
  //----- 요청 주소 : /b/detail?bid=1&code=detail&url=abc
  @RequestMapping("/detail")
  public String methodB(@RequestParam(name = "bid") int bid  //-- @RequestParam 어노테이션으로 요청 파라미터를 처리할 수 있습니다.
                      , @RequestParam String code  //------------ name을 생략하면 요청 파라미터를 변수 이름으로 추론하여 결정합니다.
                      , String url) {  //------------------------ @RequestParam 어노테이션을 생략하면 요청 파라미터를 변수 이름으로 추론하여 결정합니다.
    
    // @RequestParam 사용 시 주의사항
    // 1. @RequestParam 어노테이션을 명시하는 경우 @RequestParam(required = true)가 디폴트 설정이므로, 파라미터를 전달하지 않으면 400 에러가 발생합니다.
    // 2. @RequestParam 어노테이션을 생략하는 경우 내부적으로 required = false로 처리하여 값을 가져옵니다.
    // 3. @RequestParam(required = false, defaultValue = "0") int bid 방식으로 처리하면, 요청 파라미터가 없는 경우 bid = 0이 됩니다.
    
    //----- 결과 확인
    System.out.println("bid : " + bid);
    System.out.println("code : " + code);
    System.out.println("url : " + url);
    
    return "b/detail";
    
  }
  
  //----- 요청 주소 : /b/regist?title=제목&hit=0
  @RequestMapping("/regist")
  public String methodC(BoardDTO board) {  //----- 커맨드 객체를 이용한 요청 파라미터 처리 : Constructor 또는 Setter를 이용해서 요청 파라미터를 BoardDTO의 멤버 변수(필드)에 저장합니다.
    
    //----- 결과 확인
    System.out.println("board : " + board);
    
    return "b/regist";
    
  }
  
  //----- 요청 주소 : /b/boards/1  (마지막 1은 경로에 포함된 값으로 경로 변수(Path Variable)이라고 합니다.)
  @RequestMapping("/boards/{bid}")  //------------------------------ {bid} : 경로 변수 bid
  public String methodD(@PathVariable(name = "bid") int bid) {  //-- name을 생략하면 변수 이름과 같은 경로 변수를 받아옵니다.
    
    //----- 결과 확인
    System.out.println("bid : " + bid);
    
    return "b/detail";
    
  }
  
}

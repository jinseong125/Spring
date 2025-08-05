package org.shark.crud.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/*
 * 스프링 인터셉터 (Spring Interceptor)
 * 
 * 1. 컨트롤러로 요청이 전달되기 전, 혹은 응답이 사용자에게 반환되기 전에 동작하는 컴포넌트입니다.
 * 2. 주로 인증/인가, 로깅, 공통 데이터 처리 등 전처리와 후처리 작업에 활용됩니다.
 * 3. 개입 시점에 따른 구분
 *    1) preHandle       : 컨트롤러 실행 전 호출 (반환값에 따라 컨트롤러의 실행 여부를 제어할 수 있습니다.)
 *    2) postHandle      : 컨트롤러 실행 후 뷰 렌더링 이전에 호출
 *    3) afterCompletion : 뷰 렌더링 완료 후 호출
 * 4. 기본 구현 및 등록 방법
 *    1) 인터셉터 클래스 작성 (HandlerInterceptor 인터페이스 구현)
 *      public class CustomInterceptor implements HandlerInterceptor {
 *        @Override
 *        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 *          // 사전 처리 로직
 *          return true; // false 반환 시 요청 중단
 *        }
 *      }
 *    2) 인터셉터 등록 (servlet-context.xml <interceptor> 태그 등록)
 *      <interceptors>
 *        <interceptor>
 *          <mapping path="/board/write" />
 *          <mapping path="/user/myinfo" />
 *          <beans:bean class="org.shark.crud.interceptor.LoginCheckInterceptor" />
 *        </interceptor>
 *      </interceptors>
 */

/**
 * 로그인이 안 된 상태에서는
 * 게시글 작성 화면 등으로 이동할 수 없도록 요청을 제어하는 인터셉터
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

  /**
   * 가로채기한 요청(request)과 응답(response)을 사용하는 preHandle() 메소드
   * 컨트롤러의 요청을 "수행하기 전"에 실행하기 때문에 메소드 이름이 preHandle(미리 처리) 입니다.
   * 가로채기한 요청을 정상적으로 수행하려면 true 반환, 아니면 false 반환합니다.
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    //----- 로그인 상태인지 확인하기 위해서 세션에서 nickname 가져오기
    HttpSession session = request.getSession();
    Object nickname = session.getAttribute("nickname");
    //----- 로그인 상태이면 통과 (컨트롤러 호출)
    if (nickname != null) {
      return true;
    }
    //----- 로그인 상태가 아니면, 로그인 페이지로 이동 시키기
    response.setHeader("Content-Type", "text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<script>");
    out.println("if(confirm('로그인이 필요한 기능입니다. 로그인을 할까요?')) {");
    out.println("location.href='" + request.getContextPath() + "/user/login?url=" + request.getRequestURL() + "'");
    out.println("} else {");
    out.println("history.back()");
    out.println("}");
    out.println("</script>");
    out.close();
    return false;
  }
  
}

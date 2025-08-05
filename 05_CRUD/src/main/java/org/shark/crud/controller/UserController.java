package org.shark.crud.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shark.crud.model.dto.UserDTO;
import org.shark.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;

  @GetMapping("/login")
  public String loginForm(String url
                        , Model model) {
    model.addAttribute("url", url);  //----- 로그인 페이지로 url을 전달합니다.
    return "user/login";  //---------------- user 폴더 아래 login.jsp로 forward 합니다.
  }
  
  @PostMapping("/login")
  public String login(RedirectAttributes redirectAttr  //----- 로그인 실패 시 에러메시지 담아서 다시 로그인 페이지로 redirect 하기 위함입니다.  
                    , HttpServletRequest request  //---------- 로그인 성공 시 세션에 nickname 올리기 위함입니다.
                    , UserDTO user  //------------------------ 요청 파라미터 email, password를 받습니다.
                    , String url) {  //----------------------- 요청 파라미터 url을 받습니다.
    //----- 로그인 로직 처리
    UserDTO loginUser = userService.login(user);
    //----- 로그인 실패 시 에러메시지 담아서 다시 로그인 페이지로 redirect 하기
    if (loginUser == null) {
      redirectAttr.addFlashAttribute("error", "아이디와 비밀번호를 확인하세요.");
      if (url != null) {
        redirectAttr.addFlashAttribute("url", url);  //----- 요청 파라미터를 이용하지 않고, login.jsp 까지 직접 url을 전송합니다.
      }
      return "redirect:/user/login";
    }
    //----- 로그인 성공 시 세션에 nickname만 올리고 url로 redirect 하기
    //      세션 고정 공격(Session Fixation Attack) 방지를 위해 기존 세션 삭제 후 새로운 세션을 사용
    HttpSession oldSession = request.getSession(false);  //---- 기존 세션 가져오기 (false : 기존 세션이 존재하면 가져오고, 없으면 null 반환합니다.)
    if (oldSession != null) {
      oldSession.invalidate();  //----------------------------- 기존 세션 초기화하기
    }
    HttpSession session = request.getSession(true);  //-------- 새로운 세션 생성 후 로그인 정보를 저장 (true : 디폴트. 기존 세션이 존재하면 가져오고, 없으면 새로 만들어서 가져옵니다.)
    session.setAttribute("nickname", loginUser.getNickname());
    //----- redirect url 체크 (url 전달이 없거나 redirect 할 수 없는 서버 내부 경로가 전달되면 컨택스트 패스로 redirect 합니다.)
    String redirectURL = "/";
    if (url != null && url.startsWith(request.getContextPath())) {
      redirectURL = url;
    }
    return "redirect:" + redirectURL;
  }
  
  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    //----- 기존 세션 가져오기 (false : 기존 세션이 존재하면 가져오고, 없으면 null 반환합니다.)
    HttpSession session = request.getSession(false);
    if (session != null) {      
      session.invalidate();  //----- 세션 초기화
    }
    //----- redirect
    return "redirect:/";
  }
  
  @GetMapping("/signup")
  public String signupForm() {
    return "user/signup";
  }
  
  @PostMapping("/signup")
  public String signup(UserDTO user
                     , RedirectAttributes redirectAttr) {
    boolean result = userService.signUp(user);
    if (!result) {
      redirectAttr.addFlashAttribute("error", "회원 가입이 실패했습니다. 다시 시도하세요.");
      return "redirect:/user/signup";
    }
    redirectAttr.addFlashAttribute("msg", "회원 가입되었습니다.");
    return "redirect:/";
    
  }
  
  @ResponseBody
  @GetMapping(value = "/emailDuplicateCheck", produces = "application/json")
  public Map<String, Object> emailDuplicateCheck(String email) {
    //----- 응답 JSON 형식 : {"result": true} 또는 
    //                     {"result": false, "msg": "사용할 수 없는 이메일입니다."}
    UserDTO foundUser = userService.findUserByEmail(email);
    if (foundUser != null) {
      return Map.of("result", false, "msg", "사용할 수 없는 이메일입니다.");
    }
    return Map.of("result", true);
  }
  
}

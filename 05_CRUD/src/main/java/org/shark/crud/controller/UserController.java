package org.shark.crud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.shark.crud.model.dto.UserDTO;
import org.shark.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;
  
@GetMapping("/login")
public String loginForm(String url
                       ,Model model) {
  model.addAttribute("url", url);  //----- 로그인 페이지로 url을 전달합니다.
  return "user/login";  //---------------- user폴더 아래 login.jsp로 forword 합니다.
    
  }
@PostMapping("/login")
public String login(RedirectAttributes redirectAttr  //----- 로그인 실패시 에러메시지 담아서 다시 로그인 페이지로 redirect하기 위함입니다.
                  , HttpServletRequest request    //--------- 로그인 성공 시 세션에 nickname 올리기 위함 / contextPath를 사용하기 위함입니다.
                  , UserDTO user  //------------------------- 요청 파라미터 email, password을 받습니다.
                  , String url) {    //------------------------- 요청 파라미터 url을 받습니다.
  //----- email, password를 가진 사용자 조회하기
  UserDTO loginUser = userService.findUserByEmailAndPassword(user);
  //----- 사용자 정보가 없으면 에러메시지 담아서 다시 로그인 페이지로 redirect 하기
  if (loginUser == null) {
    redirectAttr.addFlashAttribute("error", "아이디와 비밀번호를 확인하세요.")
                .addAttribute("url", url);  //----- redirect 경로에 요청 파라미터 ?url=url을 추가하는 코드입니다.
    return "redirect:/user/login";
  }
  //----- 사용자 정보가 있으면 세션에 nickname만 올리고 url로 redirect 하기
  request.getSession().setAttribute("nickname", loginUser.getNickname());
  return "redirect:" + (url == null ? request.getContextPath() : url);  //----- url 전달이 없으면 컨텍스트 패스로 redirect 합니다.
  
}

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    //----- 세션 초기화
    session.invalidate();
    //----- redirect
    return "redirect:/";
  }
  
}

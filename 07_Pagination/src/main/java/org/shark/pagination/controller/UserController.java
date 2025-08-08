package org.shark.pagination.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.shark.pagination.model.dto.PageDTO;
import org.shark.pagination.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {

  private final UserService userService;
  
  @GetMapping("/list")
  public String list(PageDTO dto
                   , HttpServletRequest request
                   , Model model) {
    Map<String, Object> map = userService.getUsers(dto, request);
    model.addAttribute("users", map.get("users"));
    model.addAttribute("pagingHtml", map.get("pagingHtml"));
    model.addAttribute("size", map.get("size"));
    return "user/list";
  }
  
  @GetMapping("/scroll-list")
  public String scrollList() {
    return "user/scroll";
  }
  
}
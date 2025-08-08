package org.shark.pagination.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.shark.pagination.model.dto.PageDTO;
import org.shark.pagination.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;
  
  @GetMapping(value = "/scroll/list", produces = "application/json")
  public ResponseEntity<Map<String, Object>> scrollList(PageDTO dto, HttpServletRequest request) {
    System.out.println("--- 회원 리스트 요청이 들어왔습니다. ---");
    Map<String, Object> map = userService.getScrollUsers(dto, request);
    if ((int)map.get("pageCount") == 0) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().body(map);
  }
  
}

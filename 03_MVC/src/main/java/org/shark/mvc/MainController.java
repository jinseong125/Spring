package org.shark.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
  
  @RequestMapping(value = "/")  //----- "/"는 ContextPath 요청을 의미합니다.
  public String main() {
    return "main";  //----- "/WEB-INF/views/main.jsp"
  }
  
}

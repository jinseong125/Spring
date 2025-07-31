package org.shark.async.controller;

import java.util.ArrayList;
import java.util.List;

import org.shark.async.model.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @ResponseBody
 * 
 * 1. 컨트롤러 메소드 레벨의 어노테이션입니다.
 * 2. 메소드 반환값을 HTTP 응답 본문(Response Body)으로 직접 쓰도록 합니다.
 *    메소드 반환값이 JSP 이름이나 새로운 요청 경로로 인식하지 않습니다. (ViewResolver 없이 반환값을 처리합니다.)
 * 3. JSON, XML 등으로 자동 직렬화하여 HTTP 응답 본문에 담아 보냅니다.
 * 4. Jackson 같은 MessageConverter가 자바 객체를 JSON, XML 등으로 변환해서 전달합니다.
 */

@RequestMapping("/a")
@Controller
public class AsyncControllerA {

  //----- 필드
  List<BoardDTO> boards = new ArrayList<BoardDTO>();

  //----- 생성자 (필드 초기화)
  public AsyncControllerA() {
    boards.add(new BoardDTO("제목1", 20));
    boards.add(new BoardDTO("제목2", 10));
    boards.add(new BoardDTO("제목3", 30));
  }
  
  @ResponseBody
  @RequestMapping(value = {"/list", "/list.json"} //----- 2개 요청을 모두 처리합니다. 
                , produces = "application/json")  //----- 응답 데이터는 JSON 입니다.
  public List<BoardDTO> methodA() {
    return boards;
  }
  
  @ResponseBody
  @RequestMapping(value = "/list.xml"
                , produces = "application/xml")  //------ 응답 데이터는 XML 입니다.
  public List<BoardDTO> methodB() {
    return boards;
  }
  
}

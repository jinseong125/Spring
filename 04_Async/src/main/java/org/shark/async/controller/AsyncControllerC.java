package org.shark.async.controller;

import java.util.ArrayList;
import java.util.List;

import org.shark.async.model.dto.BoardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * org.springframework.http.ResponseEntity
 * 
 * 1. HTTP 응답을 세밀하게 제어하기 위한 탑클래스입니다.
 * 2. HTTP 상태 코드, 응답 헤더, 응답 본문을 포함할 수 있어 REST API 응답을 명확하고 유연하게 처리할 수 있습니다.
 * 3. 생성자 및 빌더 패턴을 지원해 다양한 방법으로 코드를 작성할 수 있습니다.
 */

@RequestMapping("/c")
@RestController
public class AsyncControllerC {

  //----- 필드
  List<BoardDTO> boards = new ArrayList<BoardDTO>();

  //----- 생성자 (필드 초기화)
  public AsyncControllerC() {
    boards.add(new BoardDTO("제목1", 20));
    boards.add(new BoardDTO("제목2", 10));
    boards.add(new BoardDTO("제목3", 30));
  }
  
  @RequestMapping(value = "/detail"  //------------------ 요청 주소 : /c/detail?bid=0 
                , produces = "application/json")  //----- 응답 데이터는 JSON 입니다.
  public ResponseEntity<BoardDTO> methodA(int bid) {
    if (bid < 0 || bid >= boards.size()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(boards.get(bid));  //------ 200과 BaorDTD 반환
  }
  
}

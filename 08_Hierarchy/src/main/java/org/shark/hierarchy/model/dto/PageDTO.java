package org.shark.hierarchy.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 페이징 정보 데이터 전송 객체
 */

@Getter
@Setter
public class PageDTO {

  private int page = 1;   //----- 현재 페이지 번호 (요청 파라미터. 디폴트 1)
  private int size = 20;  //----- 한 페이지에 표시할 항목의 개수 (요청 파라미터. 디폴트 20)
  private int offset;     //----- 각 페이지의 시작 인덱스 (PageUtil에서 계산)
  
  private int itemCount;  //----- 전체 항목의 개수 (DB에서 COUNT() 함수로 결과 계산)
  private int pageCount;  //----- 전체 페이지의 개수 (PageUtil에서 계산)
  private int beginPage;  //----- 현재 블록의 시작 페이지 (PageUtil에서 계산)
  private int endPage;    //----- 현재 블록의 끝 페이지 (PageUtil에서 계산)
  
  public PageDTO() { }
  
  public PageDTO(int page, int size, int itemCount) {
    this.page = page;
    this.size = size;
    this.itemCount = itemCount;
  }
  
}

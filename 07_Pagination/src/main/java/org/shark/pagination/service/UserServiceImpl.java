package org.shark.pagination.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.shark.pagination.model.dto.PageDTO;
import org.shark.pagination.model.dto.UserDTO;
import org.shark.pagination.repository.UserDAO;
import org.shark.pagination.util.PageUtil;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserDAO userDAO;
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> getUsers(PageDTO dto, HttpServletRequest request) {
    //----- 파라미터 sort 받기 (디폴트 DESC)
    String sort = request.getParameter("sort");
    if (sort == null || !(sort.equalsIgnoreCase("ASC") || sort.equalsIgnoreCase("DESC"))) 
      sort = "DESC";
    //----- 전체 항목의 개수를 PageDTO 객체에 저장하기
    int itemCount = userDAO.getUserCount();
    dto.setItemCount(itemCount);
    //----- 페이징 정보 계산해서 PageDTO 객체에 저장하기 (PageDTO 객체에 페이징 위한 모든 정보가 저장됩니다.)
    pageUtil.calculatePaging(dto);
    //----- 목록 가져오기
    List<UserDTO> users = userDAO.getUsers(Map.of("offset", dto.getOffset(), "size", dto.getSize(), "sort", sort));
    //----- 페이징 UI(Html) 가져오기
    String pagingHtml = pageUtil.getPagingHtml(dto, request.getContextPath() + "/user/list", Map.of("size", dto.getSize(), "sort", sort));
    //----- 결과 반환
    return Map.of("users", users, "pagingHtml", pagingHtml, "size", dto.getSize());
  }
  
  @Override
  public Map<String, Object> getScrollUsers(PageDTO dto, HttpServletRequest request) {
    //----- 파라미터 sort 받기 (디폴트 DESC)
    String sort = request.getParameter("sort");
    if (sort == null || !(sort.equalsIgnoreCase("ASC") || sort.equalsIgnoreCase("DESC"))) 
      sort = "DESC";
    //----- 전체 항목의 개수를 PageDTO 객체에 저장하기
    int itemCount = userDAO.getUserCount();
    dto.setItemCount(itemCount);
    //----- 한 번에 9개씩 가져오기
    dto.setSize(9);
    //----- 페이징 정보 계산해서 PageDTO 객체에 저장하기 (PageDTO 객체에 페이징 위한 모든 정보가 저장됩니다.)
    pageUtil.calculatePaging(dto);
    //----- 목록 가져오기
    List<UserDTO> users = userDAO.getUsers(Map.of("offset", dto.getOffset(), "size", dto.getSize(), "sort", sort));
    //----- 결과 반환 (회원 목록과 전체 페이지 개수)
    return Map.of("users", users, "pageCount", dto.getPageCount());
  }

}

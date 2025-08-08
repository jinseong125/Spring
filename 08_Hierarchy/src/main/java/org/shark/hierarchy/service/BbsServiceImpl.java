package org.shark.hierarchy.service;

import java.util.List;
import java.util.Map;

import org.shark.hierarchy.model.dto.BbsDTO;
import org.shark.hierarchy.model.dto.PageDTO;
import org.shark.hierarchy.repository.BbsDAO;
import org.shark.hierarchy.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BbsServiceImpl implements BbsService {

  private final BbsDAO bbsDAO;
  private final PageUtil pageUtil;
  
  @Override
  public boolean addBbs(BbsDTO parent) {  //----------------- 파라미터 객체 parent에는 content만 존재합니다.
    //----- 트랜잭션 처리를 위해서 RuntimeException() 발생이 필요합니다.
    try {
      int addedBbsCount = bbsDAO.insertParentBbs(parent);  //-- 전달한 인자 parent에는 bbsId가 null이지만, insert 이후 bbsId는 PK값을 가지게 됩니다.
      if (addedBbsCount != 1) {
        throw new RuntimeException("게시글 등록 실패");
      }
      int updatedBbsCount = bbsDAO.updateGroupId(parent);  //-- 전달한 인자 parent에는 bbsId와 content가 존재합니다.
      if (updatedBbsCount != 1) {
        throw new RuntimeException("GroupId 업데이트 실패");
      }
    } catch(Exception e) {
      throw new RuntimeException("게시글 등록 중 오류가 발생했습니다.", e);
    }
    return true;
  }

  @Override
  public boolean addReply(BbsDTO bbsDTO) {  //----- 파라미터 bbsDTO에는 답글의 내용이 content에 저장되어 있고,
                                            //      원글(부모)의 depth, groupId, groupOrder가 저장되어 있습니다. 
    // 트랜잭션 처리를 위해서 RuntimeException() 발생이 필요합니다.
    try {
      //----- 원글 BbsDTO 객체 생성
      BbsDTO parent = BbsDTO.builder()
                        .groupId(bbsDTO.getGroupId())
                        .groupOrder(bbsDTO.getGroupOrder())
                        .build();
      //----- 원글 BbsDTO 객체를 이용해 답글들의 groupOrder 업데이트
      bbsDAO.updateGroupOrder(parent);
      //----- 답글 BbsDTO 객체 생성
      BbsDTO child = BbsDTO.builder()
                       .content(bbsDTO.getContent())
                       .depth(bbsDTO.getDepth() + 1)
                       .groupId(bbsDTO.getGroupId())
                       .groupOrder(bbsDTO.getGroupOrder() + 1)
                       .build();
      //----- 답글 등록
      int addedChildCount = bbsDAO.insertChildBbs(child);
      if (addedChildCount != 1) {
        throw new RuntimeException("답글 등록 실패");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("답글 등록 중 오류가 발생했습니다.", e);
    }
    return true;
  }

  @Override
  public boolean removeBbs(Integer bbsId) {
    return bbsDAO.deleteBbsById(bbsId) == 1;
  }

  @Transactional(readOnly = true)
  @Override
  public Map<String, Object> getBbsList(PageDTO dto) {
    //----- 전체 항목의 개수를 PageDTO 객체에 저장하기
    int itemCount = bbsDAO.getBbsCount();
    dto.setItemCount(itemCount);
    //----- 페이징 정보 계산하기
    pageUtil.calculatePaging(dto);
    //----- 목록 가져오기
    List<BbsDTO> bbsList = bbsDAO.getBbsList(dto);
    //----- 결과 반환
    return Map.of("bbsList", bbsList, "pageDTO", dto);
  }

}

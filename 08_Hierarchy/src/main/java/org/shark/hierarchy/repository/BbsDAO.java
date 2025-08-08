package org.shark.hierarchy.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.shark.hierarchy.model.dto.BbsDTO;
import org.shark.hierarchy.model.dto.PageDTO;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BbsDAO {

  private final SqlSessionTemplate sqlSession;
  
  public int insertParentBbs(BbsDTO bbsDTO) {
    return sqlSession.insert("mybatis.mapper.bbsMapper.insertParentBbs", bbsDTO);
  }
  
  public int updateGroupId(BbsDTO bbsDTO) {
    return sqlSession.update("mybatis.mapper.bbsMapper.updateGroupId", bbsDTO);
  }

  public int updateGroupOrder(BbsDTO bbsDTO) {
    return sqlSession.update("mybatis.mapper.bbsMapper.updateGroupOrder", bbsDTO);
  }
  
  public int insertChildBbs(BbsDTO bbsDTO) {
    return sqlSession.insert("mybatis.mapper.bbsMapper.insertChildBbs", bbsDTO);
  }
  
  public int deleteBbsById(Integer bbsId) {
    return sqlSession.update("mybatis.mapper.bbsMapper.deleteBbsById", bbsId);
  }
  
  public Integer getBbsCount() {
    return sqlSession.selectOne("mybatis.mapper.bbsMapper.getBbsCount");
  }

  public List<BbsDTO> getBbsList(PageDTO dto) {
    return sqlSession.selectList("mybatis.mapper.bbsMapper.getBbsList", dto);
  }
  
}

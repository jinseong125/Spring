package org.shark.pagination.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.shark.pagination.model.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserDAO {

  private final SqlSessionTemplate sqlSession;
  
  public List<UserDTO> getUsers(Map<String, Object> map) {
    return sqlSession.selectList("mybatis.mapper.userMapper.getUsers", map);
  }
  
  public Integer getUserCount() {
    return sqlSession.selectOne("mybatis.mapper.userMapper.getUserCount");
  }
  
}
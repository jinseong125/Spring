package org.shark.crud.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.shark.crud.model.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  //----- SqlSessionTemplate template 빈(Bean) 자동 주입(@Autowired) 처리를 위한 생성자
@Repository               //----- DAO 레벨에서 사용하는 @Component
public class UserDAO {
  
  private final SqlSessionTemplate template;
  
  //----- 조회 (회원 정보)
  public UserDTO getUser(UserDTO user) {
    return template.selectOne("mybatis.mapper.userMapper.getUser", user);
  }
  
  //----- 조회
  public UserDTO getUserByNickname(String nickname) {
    return template.selectOne("mybatis.mapper.userMapper.getUserByNickname", nickname);
  }

}

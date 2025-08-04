package org.shark.crud.service;

import java.util.List;
import org.shark.crud.model.dto.BoardDTO;
import org.shark.crud.model.dto.UserDTO;
import org.shark.crud.repository.UserDAO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
/*
| 구분             | `@AllArgsConstructor`| `@RequiredArgsConstructor`      |
| -----------------+----------------------+---------------------------      |
| 생성자 포함 대상 | 모든 필드            | `final` 또는 `@NonNull` 필드만  |
| 가변 필드 포함?  | 포함됨               | 포함 안 됨                      |
| 주 사용 목적     | 테스트나 전체 주입   | 의존성 주입용 (불변성 보장)     |
 */

@RequiredArgsConstructor  //----- Spring Container에 있는 BoardDAO 타입의 반을 private final BoardDAO boardDAO에 자동 주입하기 위한 생성자
@Service  //--------------------- 서비스 레벨에서 사용하는 @Component
public class UserServiceImpl implements UserService {

  private final UserDAO userDAO;
  
  @Override
  public UserDTO findUserByEmailAndPassword(UserDTO user) {
    
    return userDAO.getUser(user);
  }
  @Override
  public UserDTO findUserByNickname(String nickname) {
    return userDAO.getUserByNickname(nickname);
  }
}
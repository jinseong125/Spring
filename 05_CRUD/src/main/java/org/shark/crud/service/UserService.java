package org.shark.crud.service;

import org.shark.crud.model.dto.UserDTO;

// 주요 네이밍 : get, find, create, add, update, modify, delete, remove 등

public interface UserService {
  UserDTO login(UserDTO user);
  UserDTO findUserByNickname(String nickname);
  UserDTO findUserByEmail(String email);
  boolean signUp(UserDTO user);
}

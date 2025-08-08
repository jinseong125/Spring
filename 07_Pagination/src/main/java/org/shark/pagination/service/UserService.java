package org.shark.pagination.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.shark.pagination.model.dto.PageDTO;

public interface UserService {
  Map<String, Object> getUsers(PageDTO dto, HttpServletRequest request);
  Map<String, Object> getScrollUsers(PageDTO dto, HttpServletRequest request);
}

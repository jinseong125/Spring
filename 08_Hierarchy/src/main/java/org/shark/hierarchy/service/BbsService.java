package org.shark.hierarchy.service;

import java.util.Map;

import org.shark.hierarchy.model.dto.BbsDTO;
import org.shark.hierarchy.model.dto.PageDTO;

public interface BbsService {
  boolean addBbs(BbsDTO parent);
  boolean addReply(BbsDTO bbsDTO);
  boolean removeBbs(Integer bbsId);
  Map<String, Object> getBbsList(PageDTO dto);
}

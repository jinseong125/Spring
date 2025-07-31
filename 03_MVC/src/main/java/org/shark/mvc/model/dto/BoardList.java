package org.shark.mvc.model.dto;

import java.util.ArrayList;
import java.util.List;

public class BoardList {

  private List<BoardDTO> boards;
  
  public BoardList() {
    boards = new ArrayList<BoardDTO>();
  }
  
  public void add(BoardDTO board) {
    boards.add(board);
  }

  public List<BoardDTO> getBoards() {
    return boards;
  }

  @Override
  public String toString() {
    return "" + boards;
  }
  
}

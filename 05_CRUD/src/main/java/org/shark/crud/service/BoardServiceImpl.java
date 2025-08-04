package org.shark.crud.service;

import java.util.List;

import org.shark.crud.model.dto.BoardDTO;
import org.shark.crud.repository.BoardDAO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //-- Spring Container에 있는  타입의 빈을
                         //     private final BoardDAO boardDAO에 자동주입 하기 위한 생성자
@Service                 //----- 서비스 레벨에서 사용하는 @Component (Spring Container에 BoardService 타입의 빈 생성)
public class BoardServiceImpl implements BoardService {
  
  private final BoardDAO boardDAO;

  @Override
  public List<BoardDTO> findAllBoards() {
    return boardDAO.getBoards();
  }
  
  @Override
  public Integer getBoardCount() {
    return boardDAO.getBoardCount();
  }

  @Override
  public BoardDTO findBoardById(Integer bid) {
    return boardDAO.getBoardById(bid);
  }

  @Override
  public boolean addBoard(BoardDTO board) {
    return boardDAO.insertBoard(board) == 1;
  }

  @Override
  public boolean modifyBoard(BoardDTO board) {
    return boardDAO.updateBoard(board) == 1;
  }

  @Override
  public boolean removeBoard(Integer bid) {
    return boardDAO.deleteBoardById(bid) == 1;
  }

}

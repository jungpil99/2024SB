package com.example.sb1011.service;

import com.example.sb1011.dto.BoardDto;

import java.util.List;

public interface BoardService {
	
	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(BoardDto board) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;

	BoardDto selectBoardView(int boardIdx) throws Exception;


}

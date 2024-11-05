package com.example.sb1101.service;


import com.example.sb1101.dto.BoardDto;
import com.example.sb1101.dto.BoardFileDto;
import com.example.sb1101.entity.Board;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {
	
	List<Board> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	void updateBoard(BoardDto board) throws Exception;

	void deleteBoard(int boardIdx) throws Exception;

	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}

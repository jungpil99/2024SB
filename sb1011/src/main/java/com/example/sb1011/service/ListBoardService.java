package com.example.sb1011.service;

import com.example.sb1011.dto.BoardDto;
import com.example.sb1011.dto.BoardListModel;
import com.example.sb1011.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListBoardService {

	public static final int COUNT_PER_PAGE = 10;

	@Autowired
	BoardMapper boardMapper;

	public BoardListModel getBoardListModel(int requestPageNumber) {
		if (requestPageNumber < 0) {
			throw new IllegalArgumentException("page number < 0 : "
					+ requestPageNumber);
		}
			int totalBoardCount = boardMapper.selectCount();

			if (totalBoardCount == 0) {
				return new BoardListModel();
			}

			int totalPageCount = calculateTotalPageCount(totalBoardCount);

			int firstRow = (requestPageNumber - 1) * COUNT_PER_PAGE;
			int endRow = firstRow + COUNT_PER_PAGE - 1;

			if (endRow > totalBoardCount) {
				endRow = totalBoardCount;
			}
			List<BoardDto> boardList = boardMapper.select(firstRow, endRow);

		BoardListModel boardListView = new BoardListModel(
				boardList, requestPageNumber, totalPageCount, firstRow, endRow);
		return boardListView;
	}

	private int calculateTotalPageCount(int totalBoardCount) { // 메서드 이름 변경
		if (totalBoardCount == 0) {
			return 0;
		}
		int pageCount = totalBoardCount / COUNT_PER_PAGE;
		if (totalBoardCount % COUNT_PER_PAGE > 0) {
			pageCount++;
		}
		return pageCount;
	}
}

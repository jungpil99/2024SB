package com.example.sb1011.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardListModel {

    private List<BoardDto> boardList;
    private int requestPageNumber;
    private int totalPageCount;
    private int firstRow;
    private int endRow;

    public BoardListModel(List<BoardDto> boardList, int requestPageNumber, int totalPageCount, int firstRow, int endRow) {
        this.boardList = boardList;
        this.requestPageNumber = requestPageNumber;
        this.totalPageCount = totalPageCount;
        this.firstRow = firstRow;
        this.endRow = endRow;
    }

    public BoardListModel() {
        this.boardList = new ArrayList<>();
    }
}

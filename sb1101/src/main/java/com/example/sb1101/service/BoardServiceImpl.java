package com.example.sb1101.service;




import com.example.sb1101.common.FileUtils;
import com.example.sb1101.dto.BoardDto;
import com.example.sb1101.dto.BoardFileDto;
import com.example.sb1101.entity.Board;
import com.example.sb1101.entity.BoardFile;
import com.example.sb1101.repository.BoardFileRepository;
import com.example.sb1101.repository.BoradRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoradRepository repository;
	
	@Autowired
	private FileUtils fileUtils;

	@Autowired
	BoardFileRepository boardFileRepository;
	
	@Override
	public List<Board> selectBoardList() throws Exception {
		return repository.findAll();
	}
	
	@Override
	public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		Board board = new Board();
		board.setTitle(boardDto.getTitle());
		board.setContents(boardDto.getContents());
		board.setCreatedDatetime(boardDto.getCreatedDatetime());

		repository.save(board);

		List<BoardFileDto> fileList = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(!CollectionUtils.isEmpty(fileList)){
			for(BoardFileDto fileDto : fileList){
				BoardFile boardFile = new BoardFile();
				boardFile.setBoard(board);
				boardFile.setOriginalFileName(fileDto.getOriginalFileName());
				boardFile.setStoredFilePath(fileDto.getStoredFilePath());
				boardFileRepository.save(boardFile);
			}
		}
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		Board board = repository.findByBoardIdx(boardIdx);
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardIdx(boardIdx);
		boardDto.setTitle(board.getTitle());
		boardDto.setContents(board.getContents());
		boardDto.setCreatedDatetime(board.getCreatedDatetime());

		List<BoardFile> fileList = board.getFileList();
		List<BoardFileDto> fileDtoList = new ArrayList<>();
		for(BoardFile file : fileList){
			BoardFileDto fileDto = new BoardFileDto();
			fileDto.setBoardIdx(boardIdx);
			fileDto.setOriginalFileName(file.getOriginalFileName());
			fileDto.setStoredFilePath(file.getStoredFilePath());
			fileDto.setFileSize(file.getFileSize());
			fileDtoList.add(fileDto);
		}
		boardDto.setFileList(fileDtoList);

		board.setHitCnt(boardDto.getHitCnt() + 1);
		repository.save(board);
		return boardDto;
	}
	
	@Override
	public void updateBoard(BoardDto boardDto) throws Exception {
		Board board = repository.findByBoardIdx(boardDto.getBoardIdx());

		board.setTitle(boardDto.getTitle());
		board.setContents(boardDto.getContents());
		repository.save(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		repository.deleteByBoardIdx(boardIdx);
	}

	@Override
	public BoardFileDto selectBoardFileInformation(int boardIdx, String originalFileName) throws Exception {
		return boardFileRepository.findByBoard_BoardIdxAndOriginalFileName(boardIdx, originalFileName)
				.map(boardFile -> {
					BoardFileDto boardFileDto = new BoardFileDto();

					boardFileDto.setIdx(boardFile.getIdx());
					boardFileDto.setOriginalFileName(boardFile.getOriginalFileName());
					boardFileDto.setStoredFilePath(boardFile.getStoredFilePath());
					boardFileDto.setFileSize(boardFile.getFileSize());
					return boardFileDto;
				})
				.orElseThrow(() -> new RuntimeException("File not found"));
	}
}	


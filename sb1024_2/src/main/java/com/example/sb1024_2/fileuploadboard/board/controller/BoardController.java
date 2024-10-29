package com.example.sb1024_2.fileuploadboard.board.controller;

import com.example.sb1024_2.fileuploadboard.board.dto.BoardDto;
import com.example.sb1024_2.fileuploadboard.board.dto.BoardFileDto;
import com.example.sb1024_2.fileuploadboard.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("board/openBoardList.do")
	public String openBoardList(Model model, @PageableDefault(page = 0, size = 10)Pageable pageable) throws Exception{
		log.info("====> openBoardList {}", "테스트");
//		ModelAndView mv = new ModelAndView("board/boardList");

		List<BoardDto> list = boardService.selectBoardList();
		// 현재 페이지의 시작 인덱스를 계산합니다.
		final int start = (int) pageable.getOffset();
		// Math.min()을 사용하여 리스트의 크기를 넘지 않도록 보정합니다.
		final int end = Math.min((start + pageable.getPageSize()), list.size());

		log.info("start: {}, end: {}", start, end);
		final Page<BoardDto> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

		log.info("총 페이지 수: {}", page.getTotalPages());
		log.info("전체 개수: {}", page.getTotalElements());
		log.info("현재 페이지 번호: {}", page.getNumber());
		log.info("페이지당 데이터 개수: {}", page.getSize());
		log.info("다음 페이지 존재 여부: {}", page.hasNext());
		log.info("이전 페이지 존재 여부: {}", page.hasPrevious());
		log.info("시작페이지(0) 입니까: {}", page.isFirst());
		model.addAttribute("list", page);
		
		return "/board/boardList";
	}
	
	@RequestMapping("board/openBoardWrite.do")
	public String openBoardWrite(RedirectAttributes redirectAttributes) throws Exception{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())){
			return "board/boardWrite";
		}else {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다");
			return "redirect:/board/openBoardList.do";
		}
	}
	
	@RequestMapping("board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping("board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("board/downloadBoardFile.do")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		String currentPath = Paths.get("").toAbsolutePath().toString();
		System.out.println("---------------------"+currentPath);
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File("./src/main/resources/static"+boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}

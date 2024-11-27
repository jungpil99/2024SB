package com.example.sample.Board.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.entity.BoardView;
import com.example.sample.Board.repository.BoardRepository;
import com.example.sample.Board.repository.BoardViewRepository;
import com.example.sample.Board.service.BoardService;
import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.repository.ReplyRepository;
import com.example.sample.reply.service.ReplyService;
import com.example.sample.spring.AuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {

    @Autowired
    BoardRepository repository;

    @Autowired
    BoardService boardService;

    @Autowired
    ReplyService replyService;

    @Autowired
    BoardViewRepository boardViewRepository;


//    게시판 조회
    @GetMapping("/boardList")
    public String boardList(Model model, @RequestParam(value = "query", required = false) String query,@PageableDefault(page = 0, size = 10) Pageable pageable) throws Exception{
        log.info("====> openBoardList {}", "테스트");

        List<Board> list;

        if (query != null && !query.isEmpty()) {
            // 제목으로 검색된 게시글 리스트 가져오기
            list = boardService.searchBoards(query);  // searchBoards 메소드에서 제목으로 검색
        } else {
            // 전체 게시글 리스트 가져오기
            list = boardService.selectBoardList();  // 전체 리스트를 가져오는 메소드
        }

        list.sort(Comparator.comparing(Board::getBoardIdx).reversed());

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

        log.info("start: {}, end: {}", start, end);
        final Page<Board> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        log.info("총 페이지 수: {}", page.getTotalPages());
        log.info("전체 개수: {}", page.getTotalElements());
        log.info("현재 페이지 번호: {}", page.getNumber());
        log.info("페이지당 데이터 개수: {}", page.getSize());
        log.info("다음 페이지 존재 여부: {}", page.hasNext());
        log.info("이전 페이지 존재 여부: {}", page.hasPrevious());
        log.info("시작페이지(0) 입니까: {}", page.isFirst());
        model.addAttribute("list", page);
        model.addAttribute("query", query);

        return "/board/boardList";
    }

//    게시판 글 작성창 열기
    @GetMapping("/boardWrite")
    public String boardWrite(HttpSession session, Model model, RedirectAttributes redirectAttributes) throws Exception{
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo != null && authInfo.getName() != null && !authInfo.getName().isEmpty()) {
            // authInfo가 존재하고 name이 비어있지 않으면, 작성자 값을 모델에 추가
            model.addAttribute("authorName", authInfo.getName());
            // 사용자가 로그인된 상태이면 글쓰기 페이지로 이동
            return "board/boardWrite";
        } else {
            // 로그인되지 않은 경우, 에러 메시지와 함께 게시판 목록 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다");
            return "redirect:/board/boardList";
        }
    }

//    데이터 삽입
    @PostMapping("/insertBoard")
    public String insertBoard(@RequestParam("title") String title,
                              @RequestParam("contents") String contents,
                              HttpSession session,
                              MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{

        boardService.insertBoard(session, title, contents);

        return "redirect:/board/boardList";
    }

//    테이블 보기
    @GetMapping("/boardDetail")
    public String boardDetail(Model model, @RequestParam("boardIdx") Integer boardIdx, HttpSession session,
                              @PageableDefault(page = 0, size = 3) Pageable pageable) throws Exception{
        Page<Reply> page = replyService.selectReply(pageable, boardIdx);

        model.addAttribute("list", page);  // 페이지 데이터를 전달

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Optional<Board> optionalBoard = boardService.selectBoardDetail(boardIdx);

        if(authInfo != null && authInfo.getName() != null && !authInfo.getName().isEmpty()) {
            model.addAttribute("User", authInfo.getName());

            // 로그인한 사용자의 게시글 조회 여부 확인
            boolean hasViewed = boardViewRepository.existsByUsernameAndBoardIdx(
                    authInfo.getName(), boardIdx);

            if (!hasViewed) {
                // 처음 조회하는 경우에만 조회수 증가
                Board board = optionalBoard.get();
                board.setHitCnt(board.getHitCnt() + 1);
                boardService.updateBoard(board);

                // 조회 기록 저장
                BoardView boardView = new BoardView(authInfo.getName(), boardIdx, LocalDateTime.now().toString().substring(0, 10));
                boardViewRepository.save(boardView);
            }
        }

        if (optionalBoard.isPresent()) {
            model.addAttribute("board", optionalBoard.get());
        } else {
            model.addAttribute("error", "게시글을 찾을 수 없습니다.");
            return "error";
        }
        return "board/boardDetail";
    }

//    테이블 삭제
    @PostMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("boardIdx") Integer boardIdx, HttpSession session, Model model,
                              RedirectAttributes redirectAttributes) throws Exception{
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        Board board = boardService.selectBoardDetail(boardIdx).orElse(null);


        if (board == null) {
            // 게시글이 없는 경우 처리
            model.addAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
            return "error";
        }
        //권한이 admin일 경우 처리
        if(authInfo != null && authInfo.getRole().equals("Admin")) {
            //admin일 경우 작성자와 관계없이 삭제 가능
            boardService.deleteBoard(boardIdx);
            return "redirect:/board/boardList";
        }

        // 게시글 작성자와 현재 로그인된 사용자가 같은지 확인
        if (authInfo != null && authInfo.getName().equals(board.getUsername())) {
            // 작성자일 경우 삭제
            boardService.deleteBoard(boardIdx);
            return "redirect:/board/boardList";  // 게시글 목록 페이지로 리디렉션
        } else {
            // 작성자가 아닐 경우 오류 메시지 전달
            redirectAttributes.addFlashAttribute("errorMessages", "작성자가 아니므로 삭제할 수 없습니다.");
            return "redirect:/board/boardDetail?boardIdx=" + boardIdx;  // 동일한 게시글 상세 화면으로 리디렉션
        }
    }

    @GetMapping("/boardSearch")
    public String boardSearch(@RequestParam("query") String title, Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) throws UnsupportedEncodingException {
        String encodedTitle = URLEncoder.encode(title, "UTF-8");

        List<Board> list = repository.findByTitleContaining(title);

        list.sort(Comparator.comparing(Board::getBoardIdx).reversed());

        final int start = (int) pageable.getOffset();
        final int end = Math.min(start + pageable.getPageSize(), list.size());

        final Page<Board> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

        model.addAttribute("list", page);
        model.addAttribute("query", title);

        return "redirect:/board/boardList?query=" + encodedTitle;
    }

    @GetMapping("/boardUpdate")
    public String boardUpdate(Model model, HttpSession session, @RequestParam("boardIdx") Integer boardIdx, RedirectAttributes redirectAttributes) throws Exception{
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Optional<Board> optionalBoard = boardService.selectBoardDetail(boardIdx);

        Board board = optionalBoard.get();


        if(authInfo != null && authInfo.getName().equals(board.getUsername())) {
            model.addAttribute("board", board);
            return "board/boardUpdate";
        }else{
            redirectAttributes.addFlashAttribute("errorMessages", "권한이 없습니다");
            return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
        }

    }

    @PostMapping("/updateBoard")
    public String Update(@RequestParam("boardIdx") Integer boardIdx,
                         @RequestParam("title") String title,
                         @RequestParam("contents") String contents,
                         HttpSession session){

        Optional<Board> optionalBoard = boardService.selectBoardDetail(boardIdx);

        if(optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setTitle(title);
            board.setContents(contents);
            board.setUpdatedDatetime(LocalDateTime.now().toString().substring(0,10));
            boardService.updateBoard(board);
        }

        return "redirect:/board/boardDetail?boardIdx=" + boardIdx;
    }
}

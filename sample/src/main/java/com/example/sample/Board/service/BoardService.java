package com.example.sample.Board.service;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.repository.BoardRepository;
import com.example.sample.spring.AuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    public List<Board> selectBoardList(){
        return repository.findAll(Sort.by(Sort.Order.desc("boardIdx")));
    }

    public List<Board> selectListUserName(String username){
        return repository.findByUsername(username);
    }

    public void insertBoard(HttpSession session,
                            @RequestParam("title") String title,
                            @RequestParam("contents") String contents){
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        Board board = Board.builder()
                    .title(title)
                    .createdDatetime(LocalDateTime.now().toString().substring(0, 10))
                    .contents(contents)
                    .deletedYn("N")
                    .hitCnt(0)
                    .replyCnt(0)
                    .username(authInfo.getName())
                    .build();
        repository.save(board);
    }

    public Optional<Board> selectBoardDetail(Integer boardIdx){
        return repository.findById(boardIdx);
    }

    public void deleteBoard(Integer boardIdx){
        repository.deleteById(boardIdx);
    }

    public void deleteBoardByUsername(String username){
        repository.deleteByUsername(username);
    }

    public void updateBoard(Board board){repository.save(board);}

    public List<Board> searchBoards(String title) {
        return repository.findByTitleContaining(title);
    }
}

package com.example.sample.controller;

import com.example.sample.Board.entity.Board;
import com.example.sample.Board.repository.BoardRepository;
import com.example.sample.Board.service.BoardService;
import com.example.sample.notice.entity.Notice;
import com.example.sample.notice.entity.PostDto;
import com.example.sample.notice.service.NoticeService;
import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.service.ReplyService;
import com.example.sample.repository.MemberRepository;
import com.example.sample.review.entity.Review;
import com.example.sample.review.sevice.ReviewService;
import com.example.sample.spring.AuthInfo;
import com.example.sample.spring.ChangePasswordService;
import com.example.sample.spring.Member;
import com.example.sample.spring.WrongIdPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReplyService replyService;

    @Autowired
    BoardService boardService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ChangePasswordService changePasswordService;

    @GetMapping("/userMain")
    public String user(@SessionAttribute("authInfo") AuthInfo authInfo, Model model) {
//        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo != null) {
            model.addAttribute("id", authInfo.getId());
            model.addAttribute("email", authInfo.getEmail());
            model.addAttribute("name", authInfo.getName());
        }

        return "/user/userMain";
    }

    @GetMapping("/userList")
    public String userList(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request) {
//        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<Member> members = memberRepository.findAll();

        boolean isAdmin = (boolean) request.getAttribute("isAdmin");

        if (isAdmin) {
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("user", members);
        }

        return "/user/userList";
    }

    @GetMapping("/userReply")
    public String userReply(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request) {
//        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        boolean isAdmin = (boolean) request.getAttribute("isAdmin");

        if (isAdmin) {
            model.addAttribute("isAdmin", isAdmin);
        }

        if (authInfo != null) {
            List<Reply> reply = replyService.selectReplyByUserName(authInfo.getName());
            if (!reply.isEmpty()) {
                model.addAttribute("reply", reply);
            }
        }

        return "/user/userReply";
    }

    @GetMapping("/userBoard")
    public String userBoard(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request) {

        boolean isAdmin = (boolean) request.getAttribute("isAdmin");

        if (isAdmin) {
            model.addAttribute("isAdmin", isAdmin);
        }

        if (authInfo != null) {
            List<PostDto> posts = new ArrayList<>();

            List<Board> boards = boardService.selectListUserName(authInfo.getName());
            boards.forEach(board -> posts.add(new PostDto(board.getBoardIdx(), board.getTitle(), board.getCreatedDatetime(), "board")));

            List<Notice> notices = noticeService.selectListUserName(authInfo.getName());
            notices.forEach(notice -> posts.add(new PostDto(notice.getNoticeId(), notice.getTitle(), notice.getCreatedDatetime(), "notice")));

            // Review 목록 추가
            List<Review> reviews = reviewService.selectListUserName(authInfo.getName());
            reviews.forEach(review -> posts.add(new PostDto(review.getReviewId(), review.getTitle(), review.getCreatedDatetime(), "review")));

            model.addAttribute("posts", posts);
        }

        return "/user/userBoard";
    }

    @PostMapping("/changePwd")
    public String changePwd(@SessionAttribute("authInfo") AuthInfo authInfo, Model model,
                            @ModelAttribute("command") ChangePwdCommand pwdCmd,
                            Errors errors,
                            HttpSession session) {
        new ChangePwdCommandValidator().validate(pwdCmd, errors);
        if (errors.hasErrors()) {
            return "/user/userMain";
        }
        try {
            changePasswordService.changePassword(
                    authInfo.getEmail(),
                    pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword());
            model.addAttribute("id", authInfo.getId());
            model.addAttribute("email", authInfo.getEmail());
            model.addAttribute("name", authInfo.getName());
            model.addAttribute("modal", "비밀번호를 변경했습니다");
            return "/user/userMain";

        } catch (WrongIdPasswordException e) {
            model.addAttribute("error", "현재 비밀번호가 일치하지 않습니다");
            model.addAttribute("id", authInfo.getId());
            model.addAttribute("email", authInfo.getEmail());
            model.addAttribute("name", authInfo.getName());
            return "/user/userMain";
        }

    }

    @GetMapping("/userDelete")
    public String userDelete(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request) {

        boolean isAdmin = (boolean) request.getAttribute("isAdmin");

        if (isAdmin) {
            model.addAttribute("isAdmin", isAdmin);
        }

        return "/user/userDelete";
    }

    @PostMapping("/userDelete")
    @Transactional
    public String userDelete(@SessionAttribute("authInfo") AuthInfo authInfo, Model model,
                             @RequestParam("password") String password, HttpSession session) {

        if(authInfo != null && authInfo.getPassword().equals(password)) {
            boardService.deleteBoardByUsername(authInfo.getName());
            reviewService.deleteByUsername(authInfo.getName());
            replyService.deleteByUserName(authInfo.getName());
            memberRepository.deleteByEmail(authInfo.getEmail());
            session.invalidate();
        }

        return "redirect:/user/userMain";
    }
}

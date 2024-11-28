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
import com.example.sample.spring.*;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@Slf4j
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

    @Autowired
    MemberDao memberDao;

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
    public String userList(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request,
                           @PageableDefault(page = 0, size = 10) Pageable pageable) {
//        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<Member> members = memberRepository.findAll();

        boolean isAdmin = (boolean) request.getAttribute("isAdmin");

        if (isAdmin) {

            members.sort(Comparator.comparing(Member::getId).reversed());

            final int start = (int) pageable.getOffset();
            final int end = Math.min(start + pageable.getPageSize(), members.size());

            log.info("start: {}, end: {}", start, end);
            final Page<Member> page = new PageImpl<>(members.subList(start, end), pageable, members.size());

            model.addAttribute("page", page);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("user", members);
        }

        return "/user/userList";
    }

    @GetMapping("/userReply")
    public String userReply(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request,
                            @PageableDefault(page = 0, size = 10) Pageable pageable) {
//        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        boolean isAdmin = (boolean) request.getAttribute("isAdmin");

        if (isAdmin) {
            model.addAttribute("isAdmin", isAdmin);
        }

        if (authInfo != null) {
            List<Reply> reply = replyService.selectReplyByUserName(authInfo.getName());
            if (!reply.isEmpty()) {

                reply.sort(Comparator.comparing(Reply::getReplyId).reversed());

                final int start = (int) pageable.getOffset();
                final int end = Math.min(start + pageable.getPageSize(), reply.size());

                log.info("start: {}, end: {}", start, end);
                final Page<Reply> page = new PageImpl<>(reply.subList(start, end), pageable, reply.size());

                model.addAttribute("page", page);
            }
            model.addAttribute("reply", reply);
        }

        return "/user/userReply";
    }

    @GetMapping("/userBoard")
    public String userBoard(@SessionAttribute("authInfo") AuthInfo authInfo, Model model, HttpServletRequest request,
                            @PageableDefault(page = 0, size = 10) Pageable pageable) {

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

            posts.sort(Comparator.comparing(PostDto::getPostId).reversed());

            final int start = (int) pageable.getOffset();
            final int end = Math.min(start + pageable.getPageSize(), posts.size());

            log.info("start: {}, end: {}", start, end);
            final Page<PostDto> page = new PageImpl<>(posts.subList(start, end), pageable, posts.size());

            model.addAttribute("posts", page);
        }

        return "/user/userBoard";
    }

    @PostMapping("/changePwd")
    public String changePwd(@SessionAttribute("authInfo") AuthInfo authInfo, Model model,
                            @ModelAttribute("command") ChangePwdCommand pwdCmd,
                            Errors errors,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        new ChangePwdCommandValidator().validate(pwdCmd, errors);
        if (errors.hasErrors()) {
            return "/user/userMain";
        }
        try {
            changePasswordService.changePassword(
                    authInfo.getEmail(),
                    pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword());
            redirectAttributes.addFlashAttribute("id", authInfo.getId());
            redirectAttributes.addFlashAttribute("email", authInfo.getEmail());
            redirectAttributes.addFlashAttribute("name", authInfo.getName());
            redirectAttributes.addFlashAttribute("modal", "비밀번호를 변경했습니다");
            return "redirect:/user/userMain";

        } catch (WrongIdPasswordException e) {
            redirectAttributes.addFlashAttribute("error", "현재 비밀번호가 일치하지 않습니다");
            redirectAttributes.addFlashAttribute("id", authInfo.getId());
            redirectAttributes.addFlashAttribute("email", authInfo.getEmail());
            redirectAttributes.addFlashAttribute("name", authInfo.getName());
            return "redirect:/user/userMain";
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
    public String userDelete(@SessionAttribute("authInfo") AuthInfo authInfo,
                             @RequestParam("password") String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        Member member = memberDao.selectByEmail(authInfo.getEmail());
        if(member != null && BCrypt.checkpw(password, member.getPassword())) {
            boardService.deleteBoardByUsername(authInfo.getName());
            reviewService.deleteByUsername(authInfo.getName());
            replyService.deleteByUserName(authInfo.getName());
            memberRepository.deleteByEmail(authInfo.getEmail());
            session.invalidate();
            return "redirect:/main";
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/user/userDelete";
        }

    }


}

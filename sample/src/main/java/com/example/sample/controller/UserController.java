package com.example.sample.controller;

import com.example.sample.reply.entity.Reply;
import com.example.sample.reply.service.ReplyService;
import com.example.sample.repository.MemberRepository;
import com.example.sample.spring.AuthInfo;
import com.example.sample.spring.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReplyService replyService;

    @GetMapping("/user")
    public String user(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo != null) {
            model.addAttribute("id", authInfo.getId());
            model.addAttribute("email", authInfo.getEmail());
            model.addAttribute("name", authInfo.getName());
        }

        return "/user/userMain";
    }

    @GetMapping("userList")
    public String userList(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        List<Member> members = memberRepository.findAll();

        if (authInfo != null) {
            model.addAttribute("user", members);
        }

        return "/user/userList";
    }

    @GetMapping("/userReply")
    public String userReply(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo != null) {
            List<Reply> reply = replyService.selectReplyByUserName(authInfo.getName());
            if (!reply.isEmpty()) {
                model.addAttribute("reply", reply);
            }
        }

        return "/user/userReply";
    }
}

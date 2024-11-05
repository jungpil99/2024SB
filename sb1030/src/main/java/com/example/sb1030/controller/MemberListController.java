package com.example.sb1030.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.sb1030.spring.Member;
import com.example.sb1030.spring.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MemberListController {

	@Autowired
	private MemberDao memberDao;


	@RequestMapping("/members")
	public String list(
			@ModelAttribute("cmd") ListCommand listCommand,
			Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "member/memberList";
		}
		if (listCommand.getFrom() != null && listCommand.getTo() != null) {
			LocalDate fromDate = listCommand.getFrom();
			LocalDate toDate = listCommand.getTo();

			LocalDateTime fromDateTime = fromDate.atStartOfDay(); // 시작일
			LocalDateTime toDateTime = toDate.atTime(23, 59, 59); // 종료일

			// 변환된 LocalDateTime을 사용하여 데이터베이스 조회
			List<Member> members = memberDao.selectByRegdate(fromDateTime, toDateTime);
			model.addAttribute("members", members);
		}
		return "member/memberList";
	}

}
package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.web.SessionConst;
import com.myproject.jpaboard.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto findMember, Model model) {

//        HttpSession session = request.getSession(false);
//        // 세션이 없으면 로그인 하지 않은 사용자이므로 기존의 홈으로
//        if (session == null) {
//            return "home";
//        }
//
//        Member findMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 세션이 없으면 로그인 하지 않은 사용자이므로 기존의 홈으로
        if (findMember == null) {
            return "home";
        }

        Long memberId = findMember.getId();
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        log.info("memberId={}", memberId);

        return "loginHome";
    }
}

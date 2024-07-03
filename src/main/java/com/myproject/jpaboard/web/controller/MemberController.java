package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.web.SessionConst;
import com.myproject.jpaboard.web.form.MemberFindForm;
import com.myproject.jpaboard.web.form.SignupForm;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.service.LoginService;
import com.myproject.jpaboard.web.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final MemberRepository memberRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "signup";
        }

        Member member = new Member();
        member.setEmail(signupForm.getEmail());
        member.setPassword(signupForm.getPassword());
        member.setName(signupForm.getName());
        member.setAddress(signupForm.getAddress());


        memberService.join(member); // 실패하면 validateDuplicateEmail에 의해 예외 발생

        // 세션에 멤버 등록
        MemberSessionDto loginMember = loginService.findByLoginEmail(signupForm.getEmail());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @GetMapping("/info")
    public String memberInfo(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto findMember, Model model) {

        // 세션이 없으면 로그인 하지 않은 사용자이므로 기존의 홈으로
        if (findMember == null) {
            return "home";
        }

        model.addAttribute("findMember", findMember);

        String findMemberEmail = findMember.getEmail();

        return "memberInfo";
    }

    @GetMapping("/find")
    public String showMemberFind(Model model) {

        model.addAttribute("memberFindForm", new MemberFindForm());

        return "memberFind";
    }

    @PostMapping("/find")
    public String memberFind(@Validated @ModelAttribute MemberFindForm memberFindForm, BindingResult result, Model model) {

        if (result.hasErrors()) {
            log.info("errors={}", result);
            return "memberFind";
        }

        Member findMember = memberRepository.findByEmail(memberFindForm.getEmail()).get(0);
        model.addAttribute("password", findMember.getPassword());

        return "memberFind";
    }
}

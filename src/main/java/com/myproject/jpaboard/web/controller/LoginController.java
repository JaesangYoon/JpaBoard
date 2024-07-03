package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.web.SessionConst;
import com.myproject.jpaboard.web.form.LoginForm;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그인 화면
     */
    @GetMapping("/login")
    public String showLogin(Model model, @RequestParam(name="redirectLogin", required = false, defaultValue = "/") String currentPageUrl) {

        model.addAttribute("loginForm", new LoginForm());
        log.info("currentPageUrl={}", currentPageUrl);

        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

        if (!loginService.loginCheck(form.getEmail(), form.getPassword(), bindingResult)) {
            log.info("errors={}", bindingResult);
            return "login";
        }
        // loginCheck에 통과하면 Member 반환
        MemberSessionDto loginMember = loginService.findByLoginEmail(form.getEmail());// findByEmail 했을 때 Member 하나만 나와야한다.


        // 로그인 성공 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, @RequestParam(name="redirectLogout", required = false, defaultValue = "/") String currentPageUrl) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        log.info("currentPageUrl={}", currentPageUrl);

        return "redirect:" + currentPageUrl;

    }
}

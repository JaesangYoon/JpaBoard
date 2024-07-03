package com.myproject.jpaboard.web.service;

import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * loginCheck인데 Member 가져오는 역할까지 하고있다. refactoring 필요
     */
    public boolean loginCheck(String email, String password) {

        List<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            return false; // 아얘 존재하지 않는 이메일이면 (비밀번호 확인해볼 필요도 없이) false
        }

        for (Member member : byEmail) {
            // 입력한 비밀번호와 멤버의 비밀번호가 일치하는지 확인
            if (member.getPassword().equals(password)) {
                return true; // 일치하면 true
            }
        }
        return false; // 일치하지 않으면 false
    }

    public boolean loginCheck(String email, String password, BindingResult bindingResult) {
        // check email and password
        MemberSessionDto member = findByLoginEmail(email);
        if (member == null) { // 해당 이메일로 가입된 적이 없는 경우
            bindingResult.rejectValue("email", "notExist", "존재하지 않는 회원입니다.");
            return false;
        }

        if (!member.getPassword().equals(password)) { // 이메일은 맞지만 비밀번호가 일치하지 않는 경우
            bindingResult.rejectValue("password", "mismatch", "비밀번호가 일치하지 않습니다.");
            return false;
        } else {
            return true;  // 로그인 성공 시 해당 Member 반환
        }


    }

    public MemberSessionDto findByLoginEmail(String email) {
        List<Member> byEmail = memberRepository.findByEmail(email);

        if (byEmail.isEmpty()) {
            return null;
        }

        Member member = byEmail.get(0);
        member.getPosts().size(); // LazyInitializationException 막기 위함

        MemberSessionDto memberSessionDto = new MemberSessionDto();
        memberSessionDto.setId(member.getId());
        memberSessionDto.setName(member.getName());
        memberSessionDto.setEmail(member.getEmail());
        memberSessionDto.setPassword(member.getPassword());
        memberSessionDto.setAddress(member.getAddress());
        memberSessionDto.setPosts(member.getPosts());

        return memberSessionDto;
    }

}

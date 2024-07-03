package com.myproject.jpaboard.web.service;

import com.myproject.jpaboard.domain.Address;
import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
//        validateDuplicateEmail(member.getEmail()); // 중복 이메일이 존재하는지 확인. 만약 예외 발생하면 메시지 보여줘야 한다. "이미 존재하는 회원입니다."
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    



}

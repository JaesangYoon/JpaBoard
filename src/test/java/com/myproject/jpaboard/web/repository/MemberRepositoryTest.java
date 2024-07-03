package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Address;
import com.myproject.jpaboard.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
class MemberRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmail() {

        // 회원 저장
        Member member = new Member();
        member.setEmail("aaa@aaa.com");
        member.setPassword("123123");
        member.setName("Steven");
        member.setAddress(new Address("Busan", "TTT", "123123"));
        em.persist(member);

        // 회원 조회
        List<Member> byEmail = memberRepository.findByEmail(member.getEmail());
        Member findMember = byEmail.get(0);

        assertThat(findMember.getEmail()).isEqualTo("aaa@aaa.com");
        System.out.println("findMember = " + findMember);


    }
}
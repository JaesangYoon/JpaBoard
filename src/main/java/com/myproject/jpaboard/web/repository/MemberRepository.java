package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 조회, 파라미터 방식
    public List<Member> findByEmail(String email) {
//        return em.createQuery("select m from Member m join fetch m.posts where m.email = :email", Member.class)
        return em.createQuery("select m from Member m where m.email= :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }




}

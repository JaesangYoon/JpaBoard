package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 순수 JPA Repository
 */
@Repository
@RequiredArgsConstructor
public class BoardJpaRepository {

    private final EntityManager em;

    /**
     * 게시물 조회 (단건)
     */
    public Post findOne(Long id) {
        // 조회수 증가
        return em.find(Post.class, id);
    }

    /**
     * 게시물 조회 (전체)
     */
    public List<Post> findAll() {
        return em.createQuery("select p from Post p order by p.id desc", Post.class)
                .getResultList();
    }

    /**
     * 게시물 저장
     */
    public void save(Post post) {
        em.persist(post);
    }
}

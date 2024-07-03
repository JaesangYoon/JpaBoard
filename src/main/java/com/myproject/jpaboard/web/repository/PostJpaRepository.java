package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Comment;
import com.myproject.jpaboard.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class PostJpaRepository {

    private final EntityManager em;


    /**
     * 댓글 조회
     */
    public List<Comment> findCommentByPostId(Long id) {
        List<Comment> resultList = em.createQuery("select c from Comment c where c.post.id = :id and c.parentComment is null", Comment.class)
                .setParameter("id", id)
                .getResultList();
        return resultList;
    }

    /**
     * 댓글 저장
     */
    public void save(Comment comment) {
        em.persist(comment);
    }



//
//    /**
//     * 조회수 증가 -- PostService로 이동
//     */
//    public Long addViewCount(Long id) {
//        Post findPost = em.find(Post.class, id);
//        Long addedViewCount = findPost.getViewCount();
//        addedViewCount += 1;
//        findPost.setViewCount(addedViewCount);
//        return findPost.getViewCount();
//    }


}

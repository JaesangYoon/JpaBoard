package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 댓글 조회
     */
    @Query("select c from Comment c where c.post.id = :id and c.parentComment is null")
    List<Comment> findCommentByPostId(@Param("id") Long id);

    /**
     * 댓글 등록
     */
    Comment save(Comment comment);


    /**
     * 댓글 삭제
     */
    void delete(Comment comment);



}

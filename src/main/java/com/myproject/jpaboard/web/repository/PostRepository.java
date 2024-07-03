package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 게시물 저장
     */
    Post save(Post post);


    /**
     * 게시물 삭제
     */
    void delete(Post post);

    /**
     * 게시물 검색
     */
    Page<Post> findByTitleContainingIgnoreCaseOrderByCreatedTimeDesc(String keyword, Pageable pageable);

    Page<Post> findByWriterContainingIgnoreCaseOrderByCreatedTimeDesc(String keyword, Pageable pageable);


}




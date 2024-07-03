package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA
 */
public interface BoardRepository extends JpaRepository<Post, Long> {

    /**
     * 게시물 조회 (단건)
     */
    Optional<Post> findById(Long id);

    /**
     * 게시물 조회 (전체) + 페이징
     * 페이징 처리 된 데이터를 가져올 수 있다. (사용자가 요청한 페이지 번호에 해당하는 데이터)
     */
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT p.title FROM Post p WHERE p.member.email = :email")
    List<String> findTitlesByEmail(@Param("email") String email);



}

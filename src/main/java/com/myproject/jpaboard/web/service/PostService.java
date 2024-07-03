package com.myproject.jpaboard.web.service;

import com.myproject.jpaboard.domain.Comment;
import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.form.PostForm;
import com.myproject.jpaboard.web.repository.BoardJpaRepository;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시물 추가
     */
    @Transactional
    public Post addPost(PostForm postForm, MemberSessionDto loginMember) {

        Post post = new Post();

        Member findMember = memberRepository.findById(loginMember.getId());
        LocalDateTime now = LocalDateTime.now();

        // setter로 세팅
        post.setTitle(postForm.getTitle());
        post.setWriter(postForm.getWriter());
        post.setContent(postForm.getContent());
        if (postForm.getCreatedTime() == null) { // 테스트 데이터 때문
            post.setCreatedTime(now);
        } else {
            post.setCreatedTime(postForm.getCreatedTime());
        }

        post.setLastModifiedTime(now);
        post.setCategory(postForm.getCategory());
        post.setViewCount(0L);
        post.setWriter(loginMember.getName());

        post.setMember(findMember);

        postRepository.save(post);

        return post;
    }


    /**
     * 게시물 수정
     */


    /**
     * 게시물 삭제
     */


    /**
     * 게시물 댓글 수 조회
     */
    public int countCommentNumByPostId(Long id) {
        Post findPost = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        List<Comment> comments = findPost.getComments();
        int commentSize = comments.size();
        return commentSize;
    }

    /**
     * 조회수 증가
     */
    @Transactional
    public Long addViewCount(Long id) {
        Post findPost = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        findPost.setViewCount(findPost.getViewCount() + 1);
        return findPost.getViewCount();
    }


}

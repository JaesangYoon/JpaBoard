package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.CategoryType;
import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.form.PostForm;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.repository.PostRepository;
import com.myproject.jpaboard.web.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(value = false)
class PostControllerTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostService postService;


    @Test
    void deletePost() {
        LocalDateTime writtenTime = LocalDateTime.now();
        Member testMember = memberRepository.findById(1L);
        MemberSessionDto memberSessionDto = new MemberSessionDto();
        memberSessionDto.setName(testMember.getName());

        PostForm postForm = new PostForm();
        postForm.setTitle("Steven의 테스트 게시물");
        postForm.setContent("테스트 본문입니다.");
        postForm.setWriter(testMember.getName());
        postForm.setCategory(CategoryType.DAILY);
        postForm.setCreatedTime(writtenTime);
        postForm.setViewCount(0L);

        Post createdPost = postService.addPost(postForm, memberSessionDto);
        System.out.println("createdPost = " + createdPost);
        
        postRepository.delete(createdPost);

    }
}
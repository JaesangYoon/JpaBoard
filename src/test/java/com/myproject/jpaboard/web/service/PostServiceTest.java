package com.myproject.jpaboard.web.service;

import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest
@RequiredArgsConstructor
class PostServiceTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private PostService postService;


    @Test
    void addViewCount() {

        Optional<Post> byId = boardRepository.findById(1L);
        Post post = byId.get();
        System.out.println("post.getViewCount() = " + post.getViewCount());
        postService.addViewCount(1L);
        System.out.println("post.getViewCount2() = " + post.getViewCount());
    }
}
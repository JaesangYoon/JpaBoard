package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.repository.BoardJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
@Transactional
class BoardControllerTest {

    @Autowired
    private BoardJpaRepository boardRepository;

    @Test
    void showBoard() {
        List<Post> findAllPosts = boardRepository.findAll();

        for (Post post : findAllPosts) {
            System.out.println("post = " + post);
        }
//        log.info("findAllPosts={}", findAllPosts);


    }
}
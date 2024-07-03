package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void findById() {
        Post post = new Post();
        post.setTitle("제목");
        post.setWriter("작성자");

        boardRepository.save(post);
        Optional<Post> byId = boardRepository.findById(post.getId());
        assertThat(byId.get().getId()).isEqualTo(1L);
    }

//    @Test
//    void findAllByOrderByIdDesc() {
//        List<Post> allByOrderByIdDesc = boardRepository.findAllByOrderByIdDesc();
//        for (Post post : allByOrderByIdDesc) {
//            System.out.println("post.getId() = " + post.getId());
//        }
//
//
//    }

    @Test
    void save() {
        Post post = new Post();
        post.setTitle("안녕하세요");
        post.setWriter("작성자");
        Post savedPost = boardRepository.save(post);
        assertThat(savedPost.getTitle()).isEqualTo("안녕하세요");

    }

    @Test
    void findTitlesByEmail() {
        Post post = new Post();
        post.setTitle("안녕하세요");
        post.setWriter("Jason");

    }
}
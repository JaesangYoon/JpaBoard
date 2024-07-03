package com.myproject.jpaboard.web.repository;

import com.myproject.jpaboard.domain.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
@Rollback(value = false)
class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postJpaRepository;

    @Test
    void findCommentByPostId() {
        List<Comment> commentByPostId = postJpaRepository.findCommentByPostId(13L);

        for (Comment comment : commentByPostId) {
            System.out.println("comment = " + comment);
        }

    }

}
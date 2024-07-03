package com.myproject.jpaboard.web.form;

import com.myproject.jpaboard.domain.Comment;
import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentForm {

    private Member member;
    private Post post;
    private String content;
    private LocalDateTime createdTime; // 생성 시간
    private LocalDateTime lastModifiedTime;

    private Comment parentComment;
    private List<Comment> replies = new ArrayList<>();
}

package com.myproject.jpaboard.web.form;

import com.myproject.jpaboard.domain.CategoryType;
import com.myproject.jpaboard.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostForm {

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;
    private String writer;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;

    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private Long viewCount;

    private Member member;

    @NotNull(message = "카테고리는 공백일 수 없습니다.")
    private CategoryType category;


}

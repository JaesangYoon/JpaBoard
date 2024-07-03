package com.myproject.jpaboard.web.form;

import com.myproject.jpaboard.domain.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostEditForm {

//    private Long id;

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;
//    private String writer;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;
    private LocalDateTime lastModifiedTime;

    @NotNull(message = "카테고리는 공백일 수 없습니다.")
    private CategoryType category;

}

package com.myproject.jpaboard.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;


}

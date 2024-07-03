package com.myproject.jpaboard.web.form;

import com.myproject.jpaboard.domain.Address;
import com.myproject.jpaboard.web.form.validator.UniqueEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupForm {

    @NotBlank
    @UniqueEmail
    private String email;

    @Size(min = 5, max = 20)
    private String password;

    @NotBlank
    private String name;

    @Valid  // Address 객체 내부의 검증 애너테이션들을 활성화
    private Address address;

}

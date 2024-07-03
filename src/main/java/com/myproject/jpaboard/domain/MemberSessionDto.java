package com.myproject.jpaboard.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MemberSessionDto {

    private Long id;

    private String email;
    private String password;

    private String name;

    private Address address = new Address();
    private List<Post> posts = new ArrayList<>();


}

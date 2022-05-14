package com.example.marketkurly_clone.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int user_idx;
    private String id;
    private String pwd;
    private String name;
    private String email;
    private String phone;
    private String sex;
    private String birth;
}

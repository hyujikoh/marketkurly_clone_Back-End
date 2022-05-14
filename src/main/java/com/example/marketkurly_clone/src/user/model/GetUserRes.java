package com.example.marketkurly_clone.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int user_idx;
    private String delete_yn;
    private String id;
    private String pwd;
    private String email;
    private String phone;
    private String birth;
    private String sex;
}

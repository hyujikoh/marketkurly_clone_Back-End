package com.example.marketkurly_clone.src.user.model;

import lombok.Data;

@Data
public class PostUserAddressRes {
    private int user_idx;
    private String address_main;
    private String address_desc;
    private String default_yn;

}

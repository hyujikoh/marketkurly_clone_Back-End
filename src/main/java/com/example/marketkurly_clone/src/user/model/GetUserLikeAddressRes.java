package com.example.marketkurly_clone.src.user.model;

import lombok.Data;

@Data
public class GetUserLikeAddressRes {
    private String address_main;
    private String address_desc;
    private String default_yn;
    private String recevied_name;
    private String recevied_phone;
    private String is_like;
}

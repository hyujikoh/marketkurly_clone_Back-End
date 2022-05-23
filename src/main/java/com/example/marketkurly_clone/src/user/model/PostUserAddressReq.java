package com.example.marketkurly_clone.src.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostUserAddressReq {
    private int user_idx;
    private String address_main;
    private String address_desc;
    private String default_yn;

}

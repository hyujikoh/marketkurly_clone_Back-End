package com.example.marketkurly_clone.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PatchUserAddressReq {
    private int user_idx;
    private int address_idx;
    private String address_desc;
    private String recevied_name;
    private String recevied_phone;
    private String default_yn;
}

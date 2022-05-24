package com.example.marketkurly_clone.src.user.model;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class GetUserAddressRes {
    private int address_idx;
    private String address_main;
    private String address_desc;
    private String default_yn;
    private String recevied_name;
    private String recevied_phone;
    private String is_like;
}

package com.example.marketkurly_clone.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCheckUserInfoReq {
    private String phone;
    private String email;
    private String id;
}

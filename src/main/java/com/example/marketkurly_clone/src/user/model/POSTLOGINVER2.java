package com.example.marketkurly_clone.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class POSTLOGINVER2 {
    private int userIdx;
    private String name;
    private String accessToken;
    private String refreshToken;
}

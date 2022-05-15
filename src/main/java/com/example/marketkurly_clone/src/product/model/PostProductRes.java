package com.example.marketkurly_clone.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProductRes {
    //private int Idx;
    private int userIdx;
    private int categoryIdx;
    private String productName;
    private String productDesc;
    private int productCondition;
    private int saftyPay;
    private int isExchange;
    private int amount;
    private int includeFee;
    private int price;
    private String directtrans;
}
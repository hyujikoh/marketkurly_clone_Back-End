package com.example.marketkurly_clone.src.user.model;


import lombok.Data;

@Data
public class GetUserCartRes {
    private int  product_idx;
    private String url;
    private String name;
    private String product_desc_name;
    private int  product_amount;
    private String  price;
    private String discount_price;
    private int  idx;

}

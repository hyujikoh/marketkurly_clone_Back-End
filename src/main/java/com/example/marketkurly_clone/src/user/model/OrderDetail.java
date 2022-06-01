package com.example.marketkurly_clone.src.user.model;

import lombok.Data;

@Data
public class OrderDetail {
    private int order_idx;
    private int product_idx;
    private int idx;
    private String  price;
    private String discount;
    private String amount;
    //private String is_reviewed;
    //private String status_on_delivery;
}

package com.example.marketkurly_clone.src.user.model;

import lombok.Data;

@Data
public class paydetailproduct {
    private int order_idx;
    private int product_idx;
    private int idx;
    private String  price;
    private String discount;
    private String amount;
}

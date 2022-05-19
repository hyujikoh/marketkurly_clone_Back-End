package com.example.marketkurly_clone.src.product.model;


import lombok.*;

import java.util.List;

@Data
public class PostAddCartListReq {
    private int user_idx;
    private int product_idx;
    private List<AddCart> addCartList;
}

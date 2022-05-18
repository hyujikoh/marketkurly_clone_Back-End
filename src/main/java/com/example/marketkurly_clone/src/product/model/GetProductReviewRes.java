package com.example.marketkurly_clone.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductReviewRes {


    private int review_idx;
    private String review_title;
    private String review_desc;
    private String update_at;
    private String user_name;
    private int views;
    private int help_count;

}

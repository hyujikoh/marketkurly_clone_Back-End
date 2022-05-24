package com.example.marketkurly_clone.src.product.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDetailListRes {
    private int index;
    private String name;
    private int origin_price;
    private int discount_price;
    private String save_point_yn;
}

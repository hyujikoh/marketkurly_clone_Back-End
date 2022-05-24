package com.example.marketkurly_clone.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDetailRes {
    private int product_idx;
    private String brand_name;
    private String name;
    private String subname;
    private String price;
    private String discount;
    private String discount_price;
    private String sales_unit;
    private String weight;
    private String source;
    private String type;
    private String packaging_type;
    private String Allergie_info;
    private String distinct_deliver;
    private String notice;
    private String shelf_life;
    private String url;
    private String maxminum_purchase;







}

/**
 * private int Idx;    // 제품 인덱스
 *     private int price;  // 제품 가격
 *     private String productName; // 제품 제목
 *     private int saftyPay;   // 번개페이(안전페이) 여부        1/2
 *     private String imageUrl;
 *
 */
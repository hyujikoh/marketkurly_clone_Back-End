package com.example.marketkurly_clone.src.home.model;


import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.Getter;
        import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetHomePageRes {
    private int product_idx;    // 제품 인덱스
    private String brand_name; // 제품 이름
    private String name; // 제품 이름
    private String subname; // 제품 제목
    private String price;  // 제품 가격
    private String discount;
    private String discount_price;
    private String is_kurlyonly;
    private int sales_count;
    private String url;
    private String update_at;
    private String maxminum_purchase;
}
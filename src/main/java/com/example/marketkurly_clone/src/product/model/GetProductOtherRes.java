package com.example.marketkurly_clone.src.product.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetProductOtherRes {
// 상세 페이지에서 상품 갯수랑, 유저 좋아요 여부 던져주는것
    private int user_islike;
    private int review_cnt;
}

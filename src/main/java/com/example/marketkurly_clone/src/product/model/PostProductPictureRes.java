package com.example.marketkurly_clone.src.product.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostProductPictureRes {
    private int Idx;
    private int productIdx;
    private String imageUrl;
}

package com.example.marketkurly_clone.src.product;
import com.example.marketkurly_clone.src.product.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.*;


@Mapper
public interface ProductMapper {
     public List<GetProductSearchRes> getProductSearchRes(String keyword);
     GetProductSearchRes getProductSearchRes_key(int keyword);

     /*
     * 상품 상세 페이지 관련 데이터 처리
     * */
     List<GetProductDetailRes> getProductInfo(int product_idx);
     List<GetProductReviewRes> getReviewList(int product_idx);
     List<GetProductOtherRes> islikely(int product_idx, int userIdxByJwt);
     int reviewCount(int product_idx);


     List Product_detail_info(int product_idx);

     //장바구니 상품 존재유무 확인
     int CheckExistProduct (int product_detail_idx,int userIdxByJwt,int product_idx);
     List<GetProductSearchRes> getProductCategoryRes(int Category,int Pages);
     int getProductCount(int Category,int Pages);
     int getProductCount1(String Keyword);
     int PostAddCart(int product_detail_idx, int count,int userIdxByJwt,int product_idx);
     int UpdateAddCart(int product_detail_idx, int count,int userIdxByJwt,int product_idx);

    List<GetProductDetailListRes> Product_detail_info_keyword(int keyword);
}

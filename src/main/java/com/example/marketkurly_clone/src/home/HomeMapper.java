package com.example.marketkurly_clone.src.home;

import com.example.marketkurly_clone.src.home.model.GetHomePageRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeMapper {
    List<GetHomePageRes> getRandomProductRes();

    List<GetHomePageRes> getDiscountProductRes();

    List<GetHomePageRes> getSalesProductRes();

    List<GetHomePageRes> getKurlyProductRes();

    GetHomePageRes RandomProductInfo_key(int product_idx);
    List RandomProductInfo_key_detail(int product_idx);

    GetHomePageRes DiscountProductInfo_key(int product_idx);
    List DiscountProductInfo_key_detail(int product_idx);

    GetHomePageRes SalesProductInfo_key(int product_idx);
    List SalesProductInfo_key_detail(int product_idx);

    GetHomePageRes KurlyProductInfo_key(int product_idx);
    List KurlyProductInfo_key_detail(int product_idx);

}

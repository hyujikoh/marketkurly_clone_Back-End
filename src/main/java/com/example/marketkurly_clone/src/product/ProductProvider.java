package com.example.marketkurly_clone.src.product;

import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.src.product.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.marketkurly_clone.config.BaseResponseStatus.*;

@Service
public class ProductProvider {
    private final JwtService jwtService;
    private final ProductMapper productMapper;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductMapper productMapper, JwtService jwtService) {
        this.productMapper = productMapper;
        this.jwtService = jwtService;
    }

// 제품 상세페이지
//    public GetProductDetailRes getProductDetail(int userIdx, int productIdx) throws BaseException{
//        try{
//
//        }
//        catch (Exception exception){
//            System.out.println("provider의 catch부분");
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }





// 검색어로 제품 조회
    public List<GetProductSearchRes> getProductsBySearch(String keyword) throws BaseException{

            List<GetProductSearchRes> getProductSearchRes = productMapper.getProductSearchRes(keyword);
            return  getProductSearchRes;

    }

// 상품 상세 조회 API
    // 후에 관련된 상품(카테코리로 기준으로 랜덤 상품 조회 조회)
    public List<String> getProductDetails(int product_idx, int userIdxByJwt) {

            List Product_info = productMapper.getProductInfo(product_idx);
            List Product_detail_info = productMapper.Product_detail_info(product_idx);
            List islikely = productMapper.islikely(product_idx,userIdxByJwt);
            List getReviewList = productMapper.getReviewList(product_idx);
            List resultDetailList = new ArrayList<>(Arrays.asList(Product_info,Product_detail_info,islikely,getReviewList));
            return resultDetailList;

    }

    public List<String> getProductByCategory(int Category) throws BaseException {
        List ProductCategoryRes = productMapper.getProductCategoryRes(Category);
        List resultDetailList = new ArrayList<>(Arrays.asList(ProductCategoryRes));
        return resultDetailList;
    }
}  /** class ProductProvider 닫는괄호 **/

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


    // 상품 상세 조회 API
    // 후에 관련된 상품(카테코리로 기준으로 랜덤 상품 조회 조회)
    public List<String> getProductDetails(int product_idx, int userIdxByJwt) {

        List Product_info = productMapper.getProductInfo(product_idx);
        List Product_detail_info = productMapper.Product_detail_info(product_idx);
        List islikely = productMapper.islikely(product_idx, userIdxByJwt);
        List getReviewList = productMapper.getReviewList(product_idx);
        List resultDetailList = new ArrayList<>(Arrays.asList(Product_info, Product_detail_info, islikely, getReviewList));
        return resultDetailList;

    }

    public List<String> getProductByCategory(int Category, int Pages) throws BaseException {
        int count_List = 6*(Pages-1);
        List <GetProductSearchRes> productCategoryRes = productMapper.getProductCategoryRes(Category,count_List);
        int ProductCountRes = productMapper.getProductCount(Category,count_List);

        List array123 = new ArrayList<>();

        for (int i =0;i<ProductCountRes;i++){
            GetProductSearchRes Product_info = productMapper.getProductSearchRes_key(productCategoryRes.get(i).getProduct_idx());
            List Product_detail_info = productMapper.Product_detail_info_keyword(productCategoryRes.get(i).getProduct_idx());
            List DetailList = new ArrayList<>(Arrays.asList(Product_info, Product_detail_info));
            array123.add(DetailList);
        }
        List resultDetailList = new ArrayList<>(Arrays.asList(ProductCountRes, array123));
        return resultDetailList;
    }

    /**
     * class ProductProvider 닫는괄호
     **/
    public List<String> getProductsBySearch(String Keyword) throws BaseException {
        int ProductCountRes = productMapper.getProductCount1(Keyword);
        List<GetProductSearchRes> getProductSearchRes = productMapper.getProductSearchRes(Keyword);
        List array123 = new ArrayList<>();
        for (int i = 0; i < ProductCountRes; i++) {

            GetProductSearchRes Product_info = productMapper.getProductSearchRes_key(getProductSearchRes.get(i).getProduct_idx());
            List Product_detail_info = productMapper.Product_detail_info_keyword(getProductSearchRes.get(i).getProduct_idx());
            List DetailList = new ArrayList<>(Arrays.asList(Product_info, Product_detail_info));
            array123.add(DetailList);
        }


        List resultDetailList = new ArrayList<>(Arrays.asList(ProductCountRes, array123));
        return resultDetailList;

    }
}
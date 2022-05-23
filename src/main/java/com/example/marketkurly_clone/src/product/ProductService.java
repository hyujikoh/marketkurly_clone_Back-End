package com.example.marketkurly_clone.src.product;

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
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProductProvider productProvider;
    private final JwtService jwtService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductProvider productProvider, JwtService jwtService, ProductMapper productMapper) {
        this.productProvider = productProvider;
        this.jwtService = jwtService;
        this.productMapper = productMapper;
    }


    public List<String> PostAddCart(PostAddCartListReq postAddCartListReq, int userIdxByJwt, int product_idx) {

        int len = postAddCartListReq.getAddCartList().size();
        System.out.println(postAddCartListReq.getAddCartList().get(0));

        System.out.println("qwe");
        System.out.println(len);
        List array123 = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            int CheckExistProduct = productMapper.CheckExistProduct((postAddCartListReq.getAddCartList().get(i).getProduct_detail_idx()), userIdxByJwt, product_idx);
            if(CheckExistProduct == 1 ) {
                array123.add(productMapper.UpdateAddCart(postAddCartListReq.getAddCartList().get(i).getProduct_detail_idx(), postAddCartListReq.getAddCartList().get(i).getCount(), userIdxByJwt, product_idx));
            }
            else {
                array123.add(productMapper.PostAddCart(postAddCartListReq.getAddCartList().get(i).getProduct_detail_idx(), postAddCartListReq.getAddCartList().get(i).getCount(), userIdxByJwt, product_idx));
            }
        }
        return array123;

    }
}

package com.example.marketkurly_clone.src.product;

import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.config.BaseResponse;
import com.example.marketkurly_clone.src.product.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.marketkurly_clone.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/products")
@CrossOrigin("*")
public class ProductController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final JwtService jwtService;

    public ProductController(ProductProvider productProvider, ProductService productService, JwtService jwtService) {
        this.productProvider = productProvider;
        this.productService = productService;
        this.jwtService = jwtService;
    }

    /**
     * 검색어로 제품 조회 API
     * [GET] products/productName?Keyword=
     */
    @ResponseBody
    @GetMapping("/productName")
    public BaseResponse<List<String>> getProductBySearch(@RequestParam(required = false) String Keyword) {
        //System.out.println("키워드 컨트롤러 들어옴");
        //.out.println(Keyword);

        System.out.println("키워드 :" + Keyword);
        try {
            if (Keyword == "") { // 검색어를 입력하지 않았을 때
                System.out.println("검색어 입력 안함");
                return new BaseResponse<>(EMPTY_REQUEST); // 2004 키워드를 입력하지 않았습니다
            }
            List<String> getProductSearchRes = productProvider.getProductsBySearch(Keyword);
            if (getProductSearchRes.size() == 0) { // 검색어에 해당하는 정보가 없을 때
                System.out.println("검색어에 해당하는 정보 없음");
                return new BaseResponse<>(EMPTY_RESPONSE); // 3001 입력한 키워드에 대한 검색결과가 없습니다.
            } else {
                return new BaseResponse<>(getProductSearchRes);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 상세페이지 조회 API
     * [PATCH] /:userIdx/:progress
     *
     * @return BaseResponse<list < String>>
     * 판매중인 상품 상세페이지 조회 하여 나눔
     */

    @GetMapping("/{product_idx}") // (GET) 127.0.0.1:9000/app/hotels/:hotelIdx
    public BaseResponse<List<String>> getProductDetails(@PathVariable("product_idx") int product_idx) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            System.out.println(userIdxByJwt);
            /*//userIdx와 접근한 유저가 같은지 확인해주세요
            if (userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }*/
            //같다면 유저네임 변경
            List<String> getProductDetail = productProvider.getProductDetails(product_idx,userIdxByJwt);

            return new BaseResponse<>(getProductDetail);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }



    /**
     * 카테고리 기준 제품 조회 API
     * [GET] products/productName?Keyword=
     */
    @GetMapping("/product")
    public BaseResponse<List<String>> getProductByCategory(@RequestParam(required = true) int Category,@RequestParam(required = true) int Pages) {
        try {
//            if (Category == ) { // 검색어를 입력하지 않았을 때
//                System.out.println("검색어 입력 안함");
//                return new BaseResponse<>(EMPTY_REQUEST); // 2004 키워드를 입력하지 않았습니다
//            }
            List<String> getProductCategoryRes = productProvider.getProductByCategory(Category,Pages);
            if (getProductCategoryRes.size() == 0) { // 검색어에 해당하는 정보가 없을 때
                System.out.println("검색어에 해당하는 정보 없음");
                return new BaseResponse<>(EMPTY_RESPONSE); // 3001 입력한 키워드에 대한 검색결과가 없습니다.
            } else {
                return new BaseResponse<>(getProductCategoryRes);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

/*
*  장바구니 등록 API
*
* */
    @ResponseBody
    @PostMapping("/{product_idx}/addcart")
    public BaseResponse<List<String>> PostAddCart(@PathVariable("product_idx") int product_idx,@RequestBody PostAddCartListReq postAddCartListReq) {
        try {

            int userIdxByJwt = jwtService.getUserIdx();
            System.out.println(userIdxByJwt);
            postAddCartListReq.setUser_idx(userIdxByJwt);
            postAddCartListReq.setProduct_idx(product_idx);
            List<String> PostAddCartRes = productService.PostAddCart(postAddCartListReq,userIdxByJwt,product_idx);

            return new BaseResponse<>(PostAddCartRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
} /**
 * class ProductController 끝나는 괄호
 **/

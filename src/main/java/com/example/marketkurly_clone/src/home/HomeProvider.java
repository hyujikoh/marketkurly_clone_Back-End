package com.example.marketkurly_clone.src.home;

import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.src.home.model.GetHomePageRes;
import com.example.marketkurly_clone.src.product.ProductMapper;
import com.example.marketkurly_clone.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.marketkurly_clone.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.marketkurly_clone.config.BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR;

@Service
public class HomeProvider {
    private final JwtService jwtService;
    private final HomeMapper homeMapper;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HomeProvider(HomeMapper homeMapper, JwtService jwtService) {
        this.homeMapper = homeMapper;
        this.jwtService = jwtService;
    }

    public List<String> GetHomePage() throws BaseException {

            List array123 = new ArrayList<>();
            // 랜덤 상품 리스트
            List<GetHomePageRes> getRandomProductRes =  homeMapper.getRandomProductRes();
            List randonproductlist = new ArrayList<>();
            // 할인률 높은 상품
            List<GetHomePageRes> getDiscountProductRes =  homeMapper.getDiscountProductRes();
            List discountproductlist = new ArrayList<>();
            // 판매량 높은 상품
            List<GetHomePageRes> getSalesProductRes =  homeMapper.getSalesProductRes();
            List salesproductlist = new ArrayList<>();
            // 마켓컬리 단독상품
            List<GetHomePageRes> getKurlyProductRes =  homeMapper.getKurlyProductRes();
            List kurlyproductlist = new ArrayList<>();
            //랜덤 상품
            for(int j = 0; j<8;j++){
                GetHomePageRes RandomProductInfo = homeMapper.RandomProductInfo_key(getRandomProductRes.get(j).getProduct_idx());
                List Product_detail_info = homeMapper.RandomProductInfo_key_detail(getRandomProductRes.get(j).getProduct_idx()) ;
                List DetailList = new ArrayList<>(Arrays.asList(RandomProductInfo, Product_detail_info));
                randonproductlist.add(DetailList);
            }
            array123.add(randonproductlist);
            for(int j = 0; j<8;j++){
                GetHomePageRes RandomProductInfo = homeMapper.DiscountProductInfo_key(getDiscountProductRes.get(j).getProduct_idx());
                List Product_detail_info = homeMapper.DiscountProductInfo_key_detail(getDiscountProductRes.get(j).getProduct_idx()) ;
                List DetailList = new ArrayList<>(Arrays.asList(RandomProductInfo, Product_detail_info));
                discountproductlist.add(DetailList);

            }
            array123.add(discountproductlist);
            for(int j = 0; j<8;j++){
                GetHomePageRes RandomProductInfo = homeMapper.SalesProductInfo_key(getSalesProductRes.get(j).getProduct_idx());
                List Product_detail_info = homeMapper.SalesProductInfo_key_detail(getSalesProductRes.get(j).getProduct_idx()) ;
                List DetailList = new ArrayList<>(Arrays.asList(RandomProductInfo, Product_detail_info));
                salesproductlist.add(DetailList);

            }
            array123.add(salesproductlist);
            for(int j = 0; j<8;j++){
                GetHomePageRes RandomProductInfo = homeMapper.KurlyProductInfo_key(getKurlyProductRes.get(j).getProduct_idx());
                List Product_detail_info = homeMapper.KurlyProductInfo_key_detail(getKurlyProductRes.get(j).getProduct_idx()) ;
                List DetailList = new ArrayList<>(Arrays.asList(RandomProductInfo, Product_detail_info));
                kurlyproductlist.add(DetailList);

            }
            array123.add(kurlyproductlist);

            return array123;



    }
}

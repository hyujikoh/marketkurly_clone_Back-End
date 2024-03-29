package com.example.marketkurly_clone.src.user;


import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.src.product.model.GetProductSearchRes;
import com.example.marketkurly_clone.src.user.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import com.example.marketkurly_clone.utils.SHA256;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.marketkurly_clone.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final JwtService jwtService;
    private final UserMapper userMapper;

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public UserProvider( JwtService jwtService,UserMapper userMapper) {

        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }



    public GetUserRes getUsersByIdx(int user_idx) throws BaseException{
            GetUserRes getUsersRes = userMapper.selectMember(user_idx);
            return getUsersRes;
    }




//    public int checkEmail(String email) throws BaseException{
//        try{
//            return userDao.checkEmail(email);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        User user = userMapper.get_pwd(postLoginReq);
        String encryptPwd;
        try {
            encryptPwd=new SHA256().encrypt(postLoginReq.getPwd());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(user.getPwd().equals(encryptPwd)){
            int userIdx = user.getUser_idx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx,user.getName(),jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public POSTLOGINVER2 logIn_new(PostLoginReq postLoginReq) throws BaseException{
        User user = userMapper.get_pwd(postLoginReq);
        String encryptPwd;
        try {
            encryptPwd=new SHA256().encrypt(postLoginReq.getPwd());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(user.getPwd().equals(encryptPwd)){
            int userIdx = user.getUser_idx();
            String jwt = jwtService.createJwt_ver2(userIdx);
            String refreshjwt = jwtService.create_refresh_Jwt_ver1(userIdx);
            return new POSTLOGINVER2(userIdx,user.getName(),jwt,refreshjwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public void checkUserPhone(GetCheckUserInfoReq getCheckUserInfoReq) throws BaseException {
        try{
            int res = userMapper.checkPhone(getCheckUserInfoReq);
            System.out.println(res);
            if (res == 1 ){
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }

    public List<String> getUserCartList(int user_idx) {
        List getUserCartRes = userMapper.getUserCartList(user_idx);
        List getUserLikeAddress = userMapper.getUserLikeAddress(user_idx);
        List resultDetailList = new ArrayList<>(Arrays.asList(getUserCartRes,getUserLikeAddress));
        return resultDetailList;
    }

    public List<String> GetUserAddress(int user_idx) {
        List getUserAddressList = userMapper.GetUserAddress(user_idx);
        return getUserAddressList;
    }

    public int CheckUserFavrotite(int userIdxByJwt, int product_idx) {
        int res = userMapper.CheckUserFavrotite(userIdxByJwt,product_idx);
        return res;
    }

    public GetUserInfoBeforeCheckRes GetUserInfoBeforePayment(int user_idx) throws BaseException{
        try{

            GetUserInfoBeforeCheckRes getUserInfoBeforeCheckRes = userMapper.GetUserInfoBeforePayment(user_idx);
            return getUserInfoBeforeCheckRes;
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
/*1. 찜 상품 */
    public List<String> GetUserFavorite(int user_idx) {
        //int favoriteProductCount = userMapper.favoriteProductCount(user_idx);
        List<GetUserFavoriteRes> getProductFavoriteRes = userMapper.GetUserFavorite(user_idx);
        List result = new ArrayList<>();

        result.add(getProductFavoriteRes);
        return result;
    }
}

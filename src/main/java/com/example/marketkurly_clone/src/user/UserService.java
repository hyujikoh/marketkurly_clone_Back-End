package com.example.marketkurly_clone.src.user;



import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.src.user.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import com.example.marketkurly_clone.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.marketkurly_clone.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Autowired
    public UserService( UserProvider userProvider, JwtService jwtService,UserMapper userMapper) {
        this.userProvider = userProvider;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }




    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
//        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
//            throw new BaseException(POST_USERS_EXISTS_EMAIL);
//        }

        String enc_pwd;
        try{
            //암호화
            System.out.println("1");
            enc_pwd = new SHA256().encrypt(postUserReq.getPwd());
            postUserReq.setPwd(enc_pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        System.out.println("1");
            userMapper.joinUser(postUserReq);
            int Idx = userMapper.getIdx();
            userMapper.joinUser_address(postUserReq);
            System.out.println("userIdx : " + Idx);
            //jwt 발급.
            String jwt = jwtService.createJwt(Idx);
            System.out.println(jwt);
            return new PostUserRes(jwt,Idx,enc_pwd);

    }

//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try{
//            int result = userDao.modifyUserName(patchUserReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public PostUserAddressReq PostUpdateAddress(PostUserAddressReq postUserAddressReq) {
        // 1. 디폴트 배송지로 되어저 있는 배송지를 defalut_yn 을 n 으로 바뀐다.
        userMapper.updatedefult_to_n(postUserAddressReq.getUser_idx());
        System.out.println("update");
        // 2. 상품을 등록하는데 디폴트 주소여부를 Y로 한다.
        userMapper.CreateUserAddress(postUserAddressReq);
        return postUserAddressReq;
    }

    public PostUserAddressReq PostUserAddress(PostUserAddressReq postUserAddressReq) {
        userMapper.CreateUserAddress(postUserAddressReq);
        return postUserAddressReq;
    }


    public PatchUserAddressReq PatchUpdateAddress(PatchUserAddressReq patchUserAddressReq) {
        // 1. 디폴트 배송지로 되어저 있는 배송지를 defalut_yn 을 n 으로 바뀐다.
        userMapper.updatedefult_to_n(patchUserAddressReq.getUser_idx());
        System.out.println("update");
        // 2. 상품을 등록하는데 디폴트 주소여부를 Y로 한다.
        userMapper.updataUserAddress(patchUserAddressReq);
        return patchUserAddressReq;
    }
    public PatchUserAddressReq PatchUserAddress(PatchUserAddressReq patchUserAddressReq) {
            userMapper.updataUserAddress(patchUserAddressReq);
            return patchUserAddressReq;

    }

    public void PostUserFavorite(int userIdxByJwt, int product_idx) {

        int checkUserFavorite = userProvider.CheckUserFavrotite(userIdxByJwt,product_idx);

        if(checkUserFavorite ==1 ){
            int isDeleteOfFavorite = userMapper.isDeleteOfFavorite(userIdxByJwt,product_idx);
            if(isDeleteOfFavorite == 0){
                userMapper.UpdateDeleteUserFavorite(userIdxByJwt,product_idx);
            }
            else {
                userMapper.UpdateCreateUserFavorite(userIdxByJwt,product_idx);
            }
            // 상태값만 변경
        }
        else  {
            userMapper.CreateUserFavorite(userIdxByJwt,product_idx);// 상태값만 변경
        }
    }

    public void PatchUserLikeAddresss(int user_idx, int address_idx) {
        // 기존 즐겨찾기 주소지 그냥 주소지로 변경
        userMapper.ChangeLikeToBasicAddress(user_idx);
        userMapper.ChangeBasicToLikeAddress(user_idx,address_idx);
    }

    public void DeleteUserAddress(int user_idx, int address_idx) {
        userMapper.DeleteUserAddress(user_idx,address_idx);
    }


    public void EditCartProductCount(PatchEditCartProductCountReq patchEditCartProductCountReq) throws BaseException{
        if(patchEditCartProductCountReq.getIs_type()==1 ||patchEditCartProductCountReq.getIs_type()==0){
            userMapper.EditCartProductCount(patchEditCartProductCountReq);
        }
        else{
            throw new BaseException(RESPONSE_ERROR);
        }

    }
}

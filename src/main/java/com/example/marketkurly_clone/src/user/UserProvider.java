package com.example.marketkurly_clone.src.user;


import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.src.user.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import com.example.marketkurly_clone.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.marketkurly_clone.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService,UserMapper userMapper) {
        this.userDao = userDao;
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
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

}

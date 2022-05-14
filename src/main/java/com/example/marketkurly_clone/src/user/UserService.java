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

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService,UserMapper userMapper) {
        this.userDao = userDao;
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

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

package com.example.marketkurly_clone.src.user;

import com.example.marketkurly_clone.src.user.model.GetUserRes;
import com.example.marketkurly_clone.src.user.model.PostUserReq;
import com.example.marketkurly_clone.src.user.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {
    GetUserRes selectMember(int user_idx);
    int joinUser(PostUserReq postUserReq);
    int joinUser_address(PostUserReq postUserReq);
    int getIdx();
    User get_pwd(PostLoginReq postLoginReq);
    int checkPhone(GetCheckUserInfoReq getCheckUserInfoReq);

    List<GetUserCartRes> getUserCartList(int user_idx);


}

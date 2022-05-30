package com.example.marketkurly_clone.src.user;

import com.example.marketkurly_clone.src.user.model.GetUserRes;
import com.example.marketkurly_clone.src.user.model.PostUserReq;
import com.example.marketkurly_clone.src.user.model.*;
import org.apache.ibatis.annotations.Mapper;

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


    void updatedefult_to_n(int user_idx);

    void CreateUserAddress(PostUserAddressReq postUserAddressReq);


    List<GetUserAddressRes> GetUserAddress(int user_idx);

    void updataUserAddress(PatchUserAddressReq patchUserAddressReq);

    int CheckUserFavrotite(int userIdxByJwt, int product_idx);

    int isDeleteOfFavorite(int userIdxByJwt, int product_idx);


    void CreateUserFavorite(int userIdxByJwt, int product_idx);

    void UpdateCreateUserFavorite(int userIdxByJwt, int product_idx);

    void UpdateDeleteUserFavorite(int userIdxByJwt, int product_idx);

    List getUserLikeAddress(int user_idx);

    void ChangeLikeToBasicAddress(int user_idx);

    void ChangeBasicToLikeAddress(int user_idx, int address_idx);

    void DeleteUserAddress(int user_idx, int address_idx);
}

package com.example.marketkurly_clone.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.config.BaseResponse;
import com.example.marketkurly_clone.src.user.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.marketkurly_clone.config.BaseResponseStatus.*;
import static com.example.marketkurly_clone.utils.ValidationRegex.isRegexEmail;
@CrossOrigin("*")
@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;




    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
//    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) int user_idx) {
//        try{
////            if(Email == null){
////                List<GetUserRes> getUsersRes = userProvider.getUsers();
////                return new BaseResponse<>(getUsersRes);
////            }
////            // Get Users
//            List<GetUserRes> getUsersRes = userProvider.getUsersByIdx(user_idx);
//            return new BaseResponse<>(getUsersRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{user_idx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("user_idx") int user_idx) {
        // Get Users
        try{
            GetUserRes getUserRes = userProvider.getUsersByIdx(user_idx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     * 05/07 14:39 api 작성 시작
     */
    // Body
    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
//        if(postUserReq.getEmail() == null){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//        //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getEmail())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
        try{
            System.out.println("1");
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//            //같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//        return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }


    /**
     * 유저 정보 중복여부 체크
     *
     *
     */
    @ResponseBody
    @PostMapping("/join/check")
    public BaseResponse<String> checkUserPhone(@RequestBody GetCheckUserInfoReq getCheckUserInfoReq){
        try{
            userProvider.checkUserPhone(getCheckUserInfoReq);
            String result = "사용가능";
            return new BaseResponse<>(result);
        }

        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


/**
 * 유저 장바구니 조회 API
 *
 * */
    @ResponseBody
    @GetMapping("/{user_idx}/Cart")
    public BaseResponse<List<String>> getUserCartList(@PathVariable("user_idx")int  user_idx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            List<String> getUserCartList = userProvider.getUserCartList(user_idx);
            return new BaseResponse<>(getUserCartList);
        }

        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 유저 배송지 등록 API
     * */
    @ResponseBody
    @PostMapping("/{user_idx}/Address")
    public BaseResponse<PostUserAddressReq> PostUserAddress(@PathVariable("user_idx") int user_idx,@RequestBody PostUserAddressReq postUserAddressReq){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx!=userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            postUserAddressReq.setUser_idx(user_idx);
            if("Y".equals(postUserAddressReq.getDefault_yn() )){
                System.out.println(postUserAddressReq.getDefault_yn());
                PostUserAddressReq postUserAddressReqResult = userService.PostUpdateAddress(postUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            }

            else{
                PostUserAddressReq postUserAddressReqResult= userService.PostUserAddress(postUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            }

        }

        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 배송지 리스트 조회 API
     *
     * */
    @ResponseBody
    @GetMapping("/{user_idx}/Address")
    public BaseResponse<List<String>> GetUserAddress(@PathVariable ("user_idx") int user_idx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);

            }
            List<String> getUserAddressList = userProvider.GetUserAddress(user_idx);
            return new BaseResponse<>(getUserAddressList);
        }

        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 배송지 수정 API
     *
     * */
    @ResponseBody
    @PatchMapping("/{user_idx}/Address/{address_idx}")
    public BaseResponse<PatchUserAddressReq> PatchUserAddress(@PathVariable("user_idx") int user_idx,@PathVariable("address_idx") int address_idx , @RequestBody PatchUserAddressReq patchUserAddressReq){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);

            }
            patchUserAddressReq.setUser_idx(user_idx);
            patchUserAddressReq.setAddress_idx(address_idx);
            if("Y".equals(patchUserAddressReq.getDefault_yn() )){
                System.out.println(patchUserAddressReq.getDefault_yn());
                PatchUserAddressReq postUserAddressReqResult = userService.PatchUpdateAddress(patchUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            }

            else{
                PatchUserAddressReq postUserAddressReqResult= userService.PatchUserAddress(patchUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            }

        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 유저 즐겨찾기 배송지 수정 API
     *
     * */
    @PatchMapping("/{user_idx}/Address/{address_idx}/like")
    public BaseResponse<String> PatchUserLikeAddresss(@PathVariable("user_idx") int user_idx,@PathVariable("address_idx") int address_idx){

        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.PatchUserLikeAddresss(user_idx,address_idx);
            String result = "즐겨찾기 수정완료";
            return new BaseResponse<>(result);

        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @DeleteMapping("/{user_idx}/Address/{address_idx}/delete")
    public BaseResponse<String> DeleteUserAddress(@PathVariable("user_idx") int user_idx,@PathVariable("address_idx") int address_idx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if(user_idx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.DeleteUserAddress(user_idx,address_idx);
            String result = "주소가 삭제 되었습니다";
            return new BaseResponse<>(result);
        }
        catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




    @PostMapping("/favorite/{product_idx}")
    public BaseResponse<String> PostUserFavorite(@PathVariable("product_idx") int product_idx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            userService.PostUserFavorite(userIdxByJwt,product_idx);
            String result = "생성완료";
            return new BaseResponse<>(result);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}

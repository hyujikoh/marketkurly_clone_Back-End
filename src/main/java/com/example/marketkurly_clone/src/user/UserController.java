package com.example.marketkurly_clone.src.user;

import org.apache.tomcat.util.http.SameSiteCookies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.config.BaseResponse;
import com.example.marketkurly_clone.src.user.model.*;
import com.example.marketkurly_clone.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List < GetUserRes>>
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
     *
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{user_idx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("user_idx") int user_idx) {
        // Get Users
        try {
            GetUserRes getUserRes = userProvider.getUsersByIdx(user_idx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /users
     *
     * @return BaseResponse<PostUserRes>
     * 05/07 14:39 api 작성 시작
     */
    // Body
    @ResponseBody
    @PostMapping("/join")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!

        try {
            System.out.println("1");
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 로그인 API
     * [POST] /users/logIn
     *
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @ResponseBody
    @PostMapping("/logIn_new")
    public BaseResponse<List> logIn_new(@RequestBody PostLoginReq postLoginReq, HttpServletResponse response) {
        try {
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.

            POSTLOGINVER2 postloginver2 = userProvider.logIn_new(postLoginReq);
//            Cookie cookie = new Cookie("refreshToken","web");
//            // optional properties
//            cookie.setMaxAge(7*24*60*60); // 1 wee
//            //
//            cookie.setSecure(true);
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
//            cookie.setValue(postloginver2.getRefreshToken());
//
//            // add cookie to response
//            response.addCookie(cookie);
//            //response.addHeader("SameSite","None");
            ResponseCookie cookieqwe = ResponseCookie.from("refreshToken", postloginver2.getRefreshToken())
                    .domain("prod.hiimpedro.site")
                    .maxAge(7*24*60*60)
                    .sameSite("None")
                    .secure(true)
                    .httpOnly(true)
                    .path("/")
                    .build();
            response.addHeader("Set-Cookie", cookieqwe.toString());
            response.addHeader("Access-Control-Allow-Credentials","True");
            postloginver2.setRefreshToken("is secrect");


            List result = new ArrayList();
            result.add(postloginver2);

            result.add(HttpStatus.OK);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/{userIdx}/refresh")
    public BaseResponse<String> refresh(@PathVariable ("userIdx") int userIdx) throws BaseException {
        try{
            int userIdxByJwt = jwtService.getUserIdx_refresh();
            //userIdx와 접근한 유저가 같은지 확인
//            if (userIdx != userIdxByJwt) {
//                return new BaseResponse<>(INVALID_REFRESH_JWT);
//            }
            String jwt = jwtService.createJwt_ver2(userIdx);
            String result = "a";
            return new BaseResponse<>(jwt);
        }
        catch (BaseException exception) {
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
     */
    @ResponseBody
    @PostMapping("/join/check")
    public BaseResponse<String> checkUserPhone(@RequestBody GetCheckUserInfoReq getCheckUserInfoReq) {
        try {
            userProvider.checkUserPhone(getCheckUserInfoReq);
            String result = "사용가능";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 유저 장바구니 조회 API
     */
    @ResponseBody
    @GetMapping("/{user_idx}/Cart")
    public BaseResponse<List<String>> getUserCartList(@PathVariable("user_idx") int user_idx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            List<String> getUserCartList = userProvider.getUserCartList(user_idx);
            return new BaseResponse<>(getUserCartList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
/**
 * 장바구니 수량 조절
 * */
    @PatchMapping("/{user_idx}/Cart/{product_idx}/count")
    public BaseResponse<String> EditCartProductCount(@PathVariable("user_idx") int user_idx, @PathVariable("product_idx") int product_idx, @RequestBody PatchEditCartProductCountReq patchEditCartProductCountReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            patchEditCartProductCountReq.setUser_idx(user_idx);
            patchEditCartProductCountReq.setProduct_idx(product_idx);
            userService.EditCartProductCount(patchEditCartProductCountReq);
            String result = "수량 수정 완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 유저 배송지 등록 API
     */
    @ResponseBody
    @PostMapping("/{user_idx}/Address")
    public BaseResponse<PostUserAddressReq> PostUserAddress(@PathVariable("user_idx") int user_idx, @RequestBody PostUserAddressReq postUserAddressReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            postUserAddressReq.setUser_idx(user_idx);
            if ("Y".equals(postUserAddressReq.getDefault_yn())) {
                System.out.println(postUserAddressReq.getDefault_yn());
                PostUserAddressReq postUserAddressReqResult = userService.PostUpdateAddress(postUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            } else {
                PostUserAddressReq postUserAddressReqResult = userService.PostUserAddress(postUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 배송지 리스트 조회 API
     */
    @ResponseBody
    @GetMapping("/{user_idx}/Address")
    public BaseResponse<List<String>> GetUserAddress(@PathVariable("user_idx") int user_idx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);

            }
            List<String> getUserAddressList = userProvider.GetUserAddress(user_idx);
            return new BaseResponse<>(getUserAddressList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 배송지 수정 API
     */
    @ResponseBody
    @PatchMapping("/{user_idx}/Address/{address_idx}")
    public BaseResponse<PatchUserAddressReq> PatchUserAddress(@PathVariable("user_idx") int user_idx, @PathVariable("address_idx") int address_idx, @RequestBody PatchUserAddressReq patchUserAddressReq) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);

            }
            patchUserAddressReq.setUser_idx(user_idx);
            patchUserAddressReq.setAddress_idx(address_idx);
            if ("Y".equals(patchUserAddressReq.getDefault_yn())) {
                System.out.println(patchUserAddressReq.getDefault_yn());
                PatchUserAddressReq postUserAddressReqResult = userService.PatchUpdateAddress(patchUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            } else {
                PatchUserAddressReq postUserAddressReqResult = userService.PatchUserAddress(patchUserAddressReq);
                return new BaseResponse<>(postUserAddressReqResult);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 유저 즐겨찾기 배송지 수정 API
     */
    @PatchMapping("/{user_idx}/Address/{address_idx}/like")
    public BaseResponse<String> PatchUserLikeAddresss(@PathVariable("user_idx") int user_idx, @PathVariable("address_idx") int address_idx) {

        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.PatchUserLikeAddresss(user_idx, address_idx);
            String result = "즐겨찾기 수정완료";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    /**
     * 유저 주소지 삭제 API
     */

    @DeleteMapping("/{user_idx}/Address/{address_idx}/delete")
    public BaseResponse<String> DeleteUserAddress(@PathVariable("user_idx") int user_idx, @PathVariable("address_idx") int address_idx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.DeleteUserAddress(user_idx, address_idx);
            String result = "주소가 삭제 되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 찜 생성 API
     */
    @PostMapping("/favorite/{product_idx}")
    public BaseResponse<String> PostUserFavorite(@PathVariable("product_idx") int product_idx) {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            userService.PostUserFavorite(userIdxByJwt, product_idx);
            String result = "생성완료";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 결제전 유저 정보
     *
     *
     * */
    @GetMapping("/{user_idx}/BeforePayment")
    public BaseResponse<GetUserInfoBeforeCheckRes>GetUserInfoBeforePayment(@PathVariable("user_idx") int user_idx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetUserInfoBeforeCheckRes getUserInfoBeforeCheckRes = userProvider.GetUserInfoBeforePayment(user_idx);

            return new BaseResponse<>(getUserInfoBeforeCheckRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 결제 완료
     * */
    @ResponseBody
    @PostMapping("/{user_idx}/payment")
    public BaseResponse<List<String>>PostUserPayment(@PathVariable("user_idx") int user_idx,@RequestBody PostUserPaymentReq postUserPaymentReq){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            postUserPaymentReq.setUser_idx(user_idx);
            List<String> Result = userService.PostUserPayment(postUserPaymentReq);

            return new BaseResponse<>(Result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜목록 조회
     * */
    @ResponseBody
    @GetMapping("/{user_idx}/favorite")
    public BaseResponse<List<String>>GetUserFavorite(@PathVariable("user_idx") int user_idx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (user_idx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<String> Result = userProvider.GetUserFavorite(user_idx);

            return new BaseResponse<>(Result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}

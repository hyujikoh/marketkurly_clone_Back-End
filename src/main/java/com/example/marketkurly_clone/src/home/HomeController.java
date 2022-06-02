package com.example.marketkurly_clone.src.home;

import com.example.marketkurly_clone.config.BaseException;
import com.example.marketkurly_clone.config.BaseResponse;
import com.example.marketkurly_clone.src.user.UserProvider;
import com.example.marketkurly_clone.src.user.UserService;
import com.example.marketkurly_clone.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/app/home")
public class HomeController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HomeProvider homeProvider;

    @Autowired
    private final JwtService jwtService;


    public HomeController(HomeProvider homeProvider, JwtService jwtService) {
        this.homeProvider = homeProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<String>> GetHomePage(){
        try {

            //int userIdxByJwt = jwtService.getUserIdx(); 굳이 필요하지 않은 정보라서?
            List<String> GetHomePageRes = homeProvider.GetHomePage();

            return new BaseResponse<>(GetHomePageRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

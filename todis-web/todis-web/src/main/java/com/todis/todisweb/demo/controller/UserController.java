package com.todis.todisweb.demo.controller;

import static com.todis.todisweb.global.response.SuccessCode.CHANGE_PASSWORD;
import static com.todis.todisweb.global.response.SuccessCode.FIND_PASSWORD;
import static com.todis.todisweb.global.response.SuccessCode.JOIN_SUCCESS;
import static com.todis.todisweb.global.response.SuccessCode.LOGIN_SUCCESS;

import com.todis.todisweb.demo.domain.KakaoProfile;
import com.todis.todisweb.demo.domain.OAuthToken;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.service.UserService;
import com.todis.todisweb.global.response.ResponseForm;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseForm joinUser(@RequestBody @Valid UserDto userDto){
        userService.createUser(userDto);
        return ResponseForm.success(JOIN_SUCCESS.getCode(), JOIN_SUCCESS.getMessage(), null);
    }

    @PostMapping("/login")
    public ResponseForm loginUser(@RequestBody UserDto userDto){
        String token = userService.loginUser(userDto.getName(), userDto.getEmail(), userDto.getPassword());
        return ResponseForm.success(LOGIN_SUCCESS.getCode(), LOGIN_SUCCESS.getMessage(), token);
    }

    @PutMapping("/change_profile_image")
    public ResponseForm changeProfileImage(){return null;}

    @DeleteMapping("/signout")
    public ResponseForm signout(){return null;}


   //카카오 로그인
    @GetMapping("/kakao/")
    public @ResponseBody String kakaoCallback(String code){
        //인가코드로 엑세스토큰 받아오기
        OAuthToken oAuthToken = userService.getKakaoToken(code);

        //액세스토큰으로 유저정보 가져오기
        KakaoProfile kakaoProfile = userService.getKakaoProfile(oAuthToken);

        //userDto 객체 만들어서 회원가입 및 로그인 진행
        User user = User.builder()
                .name(kakaoProfile.properties.nickname)  //이름은 카카오에서 제공하는 닉네임
                .password(UUID.randomUUID().toString())  //비밀번호는 랜덤값으로 지정
                .email(kakaoProfile.getKakao_account().getEmail()) //이메일은 카카오에서 제공하는 이메일
                .provider("kakao")
                .build();

        //로그인 하면서 액세스토큰 반환
        return userService.kakaoLogin(user);
    }

    @PutMapping("/change_password")
    public ResponseForm changePassword(@RequestBody UserDto userDto, Authentication authentication){
        userService.changePassword(authentication.getName(), userDto.getPassword());
        return ResponseForm.success(CHANGE_PASSWORD.getCode(), CHANGE_PASSWORD.getMessage(), null);
    }

    @GetMapping("/find_password")
    public  ResponseForm findPassword(@RequestBody UserDto userDto){
        userService.setTempPassword(userDto.getEmail());
        return ResponseForm.success((FIND_PASSWORD.getCode()), FIND_PASSWORD.getMessage(), null);
    }
}

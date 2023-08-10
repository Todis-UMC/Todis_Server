package com.todis.todisweb.demo.controller;


import static com.todis.todisweb.global.response.SuccessCode.CHANGE_NAME;
import static com.todis.todisweb.global.response.SuccessCode.CHANGE_PASSWORD;
import static com.todis.todisweb.global.response.SuccessCode.FIND_PASSWORD;
import static com.todis.todisweb.global.response.SuccessCode.GET_USER_INFO;
import static com.todis.todisweb.global.response.SuccessCode.GOOGLE_LOGIN;
import static com.todis.todisweb.global.response.SuccessCode.JOIN_SUCCESS;
import static com.todis.todisweb.global.response.SuccessCode.KAKAO_LOGIN;
import static com.todis.todisweb.global.response.SuccessCode.LEAVE_USER;
import static com.todis.todisweb.global.response.SuccessCode.LOGIN_SUCCESS;
import static com.todis.todisweb.global.response.SuccessCode.MATCHED_PASSWORD;

import com.todis.todisweb.demo.domain.GoogleProfile;
import com.todis.todisweb.demo.domain.GoogleToken;
import com.todis.todisweb.demo.domain.KakaoProfile;
import com.todis.todisweb.demo.domain.OAuthToken;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.dto.UserResponseDto;
import com.todis.todisweb.demo.service.UserService;
import com.todis.todisweb.global.response.ResponseForm;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
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

   //카카오 로그인
    @GetMapping("/kakao/")
    public @ResponseBody ResponseForm kakaoCallback(String code){
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

        String token = userService.kakaoLogin(user);
        //로그인 하면서 액세스토큰 반환
        return ResponseForm.success(KAKAO_LOGIN.getCode(), KAKAO_LOGIN.getMessage(), token);
    }

    @PutMapping("/change_password")
    public ResponseForm changePassword(@RequestBody UserDto userDto, Authentication authentication){
        userService.changePassword(authentication.getName(), userDto.getPassword());
        return ResponseForm.success(CHANGE_PASSWORD.getCode(), CHANGE_PASSWORD.getMessage(), null);
    }

    @PostMapping("/find_password")
    public  ResponseForm findPassword(@RequestBody UserDto userDto){
        userService.setTempPassword(userDto.getEmail());
        return ResponseForm.success((FIND_PASSWORD.getCode()), FIND_PASSWORD.getMessage(), null);
    }
    @PutMapping("/change_name")
    public ResponseForm changeName(@RequestBody UserDto userDto, Authentication authentication){
        userService.changeName(authentication.getName(), userDto.getName());
        return ResponseForm.success(CHANGE_NAME.getCode(), CHANGE_NAME.getMessage(), null);
    }

    @DeleteMapping("/leave")
    public ResponseForm leaveUser(Authentication authentication){
        userService.leaveUser(authentication.getName());
        return ResponseForm.success(LEAVE_USER.getCode(), LEAVE_USER.getMessage(), null);
    }

    @GetMapping ("/google")
    public ResponseForm googleLogin(@RequestParam String code){
        GoogleToken googleToken = userService.getGoogleToken(code);
        GoogleProfile googleProfile = userService.getGoogleProfile(googleToken);

        User user = User.builder()
                .name(googleProfile.name)  //이름은 카카오에서 제공하는 닉네임
                .password(UUID.randomUUID().toString())  //비밀번호는 랜덤값으로 지정
                .email(googleProfile.email) //이메일은 카카오에서 제공하는 이메일
                .provider("google")
                .build();

        String token = userService.googleLogin(user);

        return ResponseForm.success(GOOGLE_LOGIN.getCode(), GOOGLE_LOGIN.getMessage(), token);
    }

    @GetMapping("/info")
    public ResponseForm getUserInfo(Authentication authentication){
        UserResponseDto response = userService.getUserInfo(authentication.getName());
        return ResponseForm.success(GET_USER_INFO.getCode(), GET_USER_INFO.getMessage(), response);
    }

    @PostMapping("/compare_password")
    public  ResponseForm compare_password(@RequestBody UserDto userDto, Authentication authentication){
        userService.comparePassword(authentication.getName(), userDto.getPassword());
        return ResponseForm.success(MATCHED_PASSWORD.getCode(), MATCHED_PASSWORD.getMessage(), null);
    }
}

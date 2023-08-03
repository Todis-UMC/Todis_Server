package com.todis.todisweb.demo.controller;

import static com.todis.todisweb.global.response.SuccessCode.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todis.todisweb.demo.domain.KakaoProfile;
import com.todis.todisweb.demo.domain.OAuthToken;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.service.UserService;
import com.todis.todisweb.global.response.ResponseForm;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

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

    //Todo 이메일찾기가 로그인없이 해야하는데 따로인증할 정보도 없는상태로 어떻게 구현할지
    @GetMapping("/find_email")
    public ResponseForm<String> findEmail(Authentication authentication){
        return ResponseForm.success(FIND_EMAIL.getCode(), FIND_EMAIL.getMessage(), authentication.getName());
    }
    //Todo 비밀번호 찾기도 마찬가지 + 디코딩 할 수 없기때문에 비밀번호 재설정으로 리다이렉션
    @GetMapping("/find_password")
    public ResponseForm<String> findPassword(Authentication authentication){
        String password = userService.findPassword(authentication.getName());
        return ResponseForm.success(FIND_PASSWORD.getCode(), FIND_PASSWORD.getMessage(), password);
    }

    @PutMapping("/change_password")
    public ResponseForm changePassword(){return null;}


    @PutMapping("/change_profile_image")
    public ResponseForm changeProfileImage(){return null;}

    @DeleteMapping("/signout")
    public ResponseForm signout(){return null;}


    // Get 요청 보내면 코드가 날라옴
    //https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=e963a78de4d72b6755264d91fb7bb784&redirect_uri=http://localhost:8080/user/kakao/
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
}

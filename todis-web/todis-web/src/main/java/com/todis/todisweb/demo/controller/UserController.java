package com.todis.todisweb.demo.controller;

import static com.todis.todisweb.global.response.SuccessCode.*;

import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.service.UserService;
import com.todis.todisweb.global.response.ResponseForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}

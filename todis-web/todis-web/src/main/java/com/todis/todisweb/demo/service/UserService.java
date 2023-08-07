package com.todis.todisweb.demo.service;


import com.todis.todisweb.demo.domain.KakaoProfile;
import com.todis.todisweb.demo.domain.OAuthToken;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;

public interface UserService {
  
    void createUser(UserDto userDto);
    String loginUser(String userName, String email, String password);
    OAuthToken getKakaoToken (String code);
    KakaoProfile getKakaoProfile(OAuthToken oAuthToken);
    String kakaoLogin(User user);
    void setTempPassword(String email);
    void changePassword(String email, String password);
    void changeNickname(String email, String nickname);
  
}

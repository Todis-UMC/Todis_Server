package com.todis.todisweb.demo.service;


import com.todis.todisweb.demo.domain.GoogleProfile;
import com.todis.todisweb.demo.domain.GoogleToken;
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
    GoogleToken getGoogleToken(String code);
    GoogleProfile getGoogleProfile(GoogleToken googleToken);
    String googleLogin(User user);
    void setTempPassword(String email);
    void changePassword(String email, String password);
    void changeName(String email, String name);
    void leaveUser(String email);
}

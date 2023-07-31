package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.UserDto;

public interface UserService {
    void createUser(UserDto userDto);
    String loginUser(String userName, String email, String password);
    String findPassword(String userName);
}

package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.security.TokenProvider;
import com.todis.todisweb.global.exception.ServiceException;
import com.todis.todisweb.global.response.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.todis.todisweb.global.response.ErrorCode.INVALID_PASSWORD;


@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, TokenProvider tokenProvider){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void createUser(UserDto userDto){

        User user = new User();
        String password = passwordEncoder.encode(userDto.getPassword());

        user.setName(userDto.getName());
        user.setPassword(password);
        user.setEmail(userDto.getEmail());

        userRepository.save(user);
        throw new ServiceException(ErrorCode.ALREADY_EXISTS);
    }

    @Override
    public String loginUser(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if(user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
            String token = tokenProvider.createToken(String.format("%s", user.getId()));
            return token;
        }else{
            throw new ServiceException(INVALID_PASSWORD);
        }
    }
}


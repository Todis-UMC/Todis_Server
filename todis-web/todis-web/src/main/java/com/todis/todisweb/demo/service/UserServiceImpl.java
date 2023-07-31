package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.security.JwtUtil;
import com.todis.todisweb.global.exception.ServiceException;
import com.todis.todisweb.global.response.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.todis.todisweb.global.response.ErrorCode.INVALID_PASSWORD;


@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDto userDto){

        User user = new User();
        String password = passwordEncoder.encode(userDto.getPassword());

        user.setName(userDto.getName());
        user.setPassword(password);
        user.setEmail(userDto.getEmail());

        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new ServiceException(ErrorCode.ALREADY_EXISTS);
        }
        userRepository.save(user);
    }

    @Override
    public String loginUser(String userName, String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            return JwtUtil.createJwt(email, secretKey, expiredMs);
        }else{
            throw new ServiceException(INVALID_PASSWORD);
        }
    }

    @Override
    public String findPassword(String email) {
        User user = userRepository.findByEmail(email);
        return user.getPassword();
    }
}


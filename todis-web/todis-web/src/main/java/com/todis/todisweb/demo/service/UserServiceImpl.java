package com.todis.todisweb.demo.service;

import static com.todis.todisweb.global.response.ErrorCode.ALREADY_EXISTS;
import static com.todis.todisweb.global.response.ErrorCode.EMAIL_ALREADY_USED;
import static com.todis.todisweb.global.response.ErrorCode.INVALID_PASSWORD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todis.todisweb.demo.domain.KakaoProfile;
import com.todis.todisweb.demo.domain.OAuthToken;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.security.JwtUtil;
import com.todis.todisweb.global.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    private long expiredMs = 1000 * 60 * 60 * 1l;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDto userDto){

        String password = passwordEncoder.encode(userDto.getPassword());

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(password)
                .provider("local")
                .gender(userDto.getGender())
                .nickname(userDto.getNickname())
                .build();

        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new ServiceException(ALREADY_EXISTS);
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

    @Override
    public OAuthToken getKakaoToken(String code) {
        RestTemplate rt = new RestTemplate();
        log.info("환경변수 테스트 = {}", client_id);
        //헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //바디 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        //헤더와 바디 합쳐서 오브젝트 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //요청 보내기
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class  //응답받을 타입
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return oAuthToken;
    }

    @Override
    public KakaoProfile getKakaoProfile(OAuthToken oAuthToken) {
        RestTemplate rt = new RestTemplate();

        //헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //헤더와 바디 합쳐서 오브젝트 생성
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        //요청 보내기
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class  //응답받을 타입
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    @Override
    public String kakaoLogin(User user) {

        User findUser = userRepository.findByEmail(user.getEmail());

        //해당 이메일의 유저가 없다면 회원가입을 합니다. + 이미 로컬로 가입한 같은 이메일 계정이 있으면 에러를 반환합니다.
        if( findUser == null){
            userRepository.save(user);
        }else if(findUser.getProvider().equals("local")){
            throw new ServiceException(EMAIL_ALREADY_USED);
        }

        return JwtUtil.createJwt(user.getEmail(), secretKey, expiredMs);    // 로그인을 진행하며 토큰을 반환합니다.
    }
}


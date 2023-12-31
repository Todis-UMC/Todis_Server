package com.todis.todisweb.demo.service;

import static com.todis.todisweb.global.response.ErrorCode.ALREADY_EXISTS;
import static com.todis.todisweb.global.response.ErrorCode.EMAIL_ALREADY_USED;
import static com.todis.todisweb.global.response.ErrorCode.ENTITY_NOT_FOUND;
import static com.todis.todisweb.global.response.ErrorCode.INVALID_PASSWORD;
import static com.todis.todisweb.global.response.ErrorCode.UNMATCHED_PASSWORD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todis.todisweb.demo.domain.GoogleProfile;
import com.todis.todisweb.demo.domain.GoogleToken;
import com.todis.todisweb.demo.domain.KakaoProfile;
import com.todis.todisweb.demo.domain.OAuthToken;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.UserDto;
import com.todis.todisweb.demo.dto.UserResponseDto;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.security.JwtUtil;
import com.todis.todisweb.global.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private final JavaMailSender javaMailSender;
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;
    @Value("${google.client_id}")
    private String google_client_id;
    @Value("${google.client_secret}")
    private String google_client_secret;
    @Value("${google.redirect_uri}")
    private String google_redirect_uri;

    private long expiredMs = 1000 * 60 * 60 * 6l;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JavaMailSender javaMailSender){

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
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
        }else if(findUser.getProvider().equals("local") || findUser.getProvider().equals("google")){
            throw new ServiceException(EMAIL_ALREADY_USED);
        }

        return JwtUtil.createJwt(user.getEmail(), secretKey, expiredMs);    // 로그인을 진행하며 토큰을 반환합니다.
    }

    @Override
    public GoogleToken getGoogleToken(String code) {
        RestTemplate rt = new RestTemplate();
        //헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //바디 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", google_client_id);
        params.add("redirect_uri", google_redirect_uri);
        params.add("code", code);
        params.add("client_secret", google_client_secret);

        //헤더와 바디 합쳐서 오브젝트 생성
        HttpEntity<MultiValueMap<String, String>> googleTokenRequest =
                new HttpEntity<>(params, headers);
        //요청 보내기
        ResponseEntity<String> response = rt.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                googleTokenRequest,
                String.class  //응답받을 타입
        );

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleToken googleToken = null;
        try {
            googleToken = objectMapper.readValue(response.getBody(), GoogleToken.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return googleToken;
    }

    @Override
    public GoogleProfile getGoogleProfile(GoogleToken googleToken) {
        RestTemplate rt = new RestTemplate();

        //헤더 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+googleToken.access_token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //헤더와 바디 합쳐서 오브젝트 생성
        HttpEntity<MultiValueMap<String, String>> googleProfileRequest =
                new HttpEntity<>(headers);

        //요청 보내기
        ResponseEntity<String> response = rt.exchange(
                "https://openidconnect.googleapis.com/v1/userinfo",
                HttpMethod.GET,
                googleProfileRequest,
                String.class  //응답받을 타입
        );

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleProfile googleProfile = null;
        try {
            googleProfile = objectMapper.readValue(response.getBody(), GoogleProfile.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return googleProfile;
    }

    @Override
    public String googleLogin(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());

        //해당 이메일의 유저가 없다면 회원가입을 합니다. + 이미 로컬로 가입한 같은 이메일 계정이 있으면 에러를 반환합니다.
        if( findUser == null){
            userRepository.save(user);
        }else if(findUser.getProvider().equals("local") || findUser.getProvider().equals("kakao")){
            throw new ServiceException(EMAIL_ALREADY_USED);
        }

        return JwtUtil.createJwt(user.getEmail(), secretKey, expiredMs);
    }

    @Override
    public void setTempPassword(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ServiceException(ENTITY_NOT_FOUND);
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String randomString = RandomStringUtils.randomAlphabetic(10);
        String tempPassword = passwordEncoder.encode(randomString);

        user.setPassword(tempPassword);
        userRepository.save(user);

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Todis 비밀번호 찾기 메일입니다.");
        simpleMailMessage.setText("임시 비밀번호: "+randomString);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void changePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ServiceException(ENTITY_NOT_FOUND);
        }
        String newPossword = passwordEncoder.encode(password);
        user.setPassword(newPossword);
        userRepository.save(user);
    }
    
    @Override
    public void changeName(String email, String name) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ServiceException(ENTITY_NOT_FOUND);
        }
        user.setName(name);
        userRepository.save(user);
    }

    @Override
    public void leaveUser(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ServiceException(ENTITY_NOT_FOUND);
        }

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto getUserInfo(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ServiceException(ENTITY_NOT_FOUND);
        }

        return new UserResponseDto(user.getName(), user.getEmail());
    }

    @Override
    public void comparePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new ServiceException(ENTITY_NOT_FOUND);
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ServiceException(UNMATCHED_PASSWORD);
        }


    }

    @Override
    public void saveCodyUrl(String email, String url){
        User user = userRepository.findByEmail(email);
        user.setCodyImage(url);
        user.setProfileImageUrl(url);
        userRepository.save(user);
    }


}


package com.todis.todisweb.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todis.todisweb.demo.domain.KakaoProfile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import com.todis.todisweb.demo.domain.OAuthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("/weather")
public class WeatherController{

    @GetMapping("/get")
    public ResponseEntity<String> getWeatherInfo() throws UnsupportedEncodingException {

        String serviceKey = "CMdmnqOO8IjX29Thhm9UJ1iEuCCSaqqQHrZJB5VZZRd4HCCIVJ%2BJoPhs8JjctSaRCpKr2GWaemdig4ySJBWziw%3D%3D";
        String encodedServiceKey = URLEncoder.encode(serviceKey, "UTF-8");

        RestTemplate rt = new RestTemplate();


        // 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", encodedServiceKey);
        params.add("dataType", "JSON");
        params.add("numOfRows", "10");
        params.add("base_date", "20230803");
        params.add("base_time", "1500");
        params.add("nx", "60");
        params.add("ny", "127");


        //헤더와 바디 합쳐서 오브젝트 생성
        HttpEntity<MultiValueMap<String, String>> WeatherInfoRequest =
                new HttpEntity<>(params);

        //요청 보내기
        ResponseEntity<String> response = rt.exchange(
                "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst",
                HttpMethod.GET,
                WeatherInfoRequest,
                String.class  //응답받을 타입
        );
    /*
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        */
        return response;

}

}
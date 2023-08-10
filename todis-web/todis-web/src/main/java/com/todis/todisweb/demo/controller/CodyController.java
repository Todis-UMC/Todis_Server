package com.todis.todisweb.demo.controller;


import static com.todis.todisweb.global.response.SuccessCode.GET_CODY_SUCCESS;
import static com.todis.todisweb.global.response.SuccessCode.GET_FRIEND_LIST;
import static com.todis.todisweb.global.response.SuccessCode.POST_SUCCESS;

import com.todis.todisweb.demo.dto.CodyDto;
import com.todis.todisweb.demo.dto.CodyResponseDto;
import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import com.todis.todisweb.demo.service.CodyService;
import com.todis.todisweb.demo.service.CodyServiceImpl;
import com.todis.todisweb.global.response.ResponseForm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class CodyController {

    @Autowired
    private CodyServiceImpl codyService;

    @PostMapping("/cody/post")
    public ResponseForm postComment(Authentication authentication, String Comment) {
        codyService.updateComment(authentication.getName(), Comment);
        return ResponseForm.success(POST_SUCCESS.getCode(), POST_SUCCESS.getMessage(), null);
    }

    @PostMapping(path = "/cody/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseForm<List<String>> postCody(
            Authentication authentication,
            @RequestPart(value = "top", required = false) MultipartFile top,
            @RequestPart(value = "bottom", required = false) MultipartFile bottom,
            @RequestPart(value = "shoes", required = false) MultipartFile shoes,
            @RequestPart(value = "acc", required = false) MultipartFile acc,
            @RequestPart(value = "topmin", required = false) MultipartFile topmin,
            @RequestPart(value = "bottommin", required = false) MultipartFile bottommin,
            @RequestPart(value = "shoesmin", required = false) MultipartFile shoesmin,
            @RequestPart(value = "accmin", required = false) MultipartFile accmin, Boolean gender) {
        List<String> url = codyService.updateCody(authentication.getName(), top, bottom,
                shoes, acc, topmin, bottommin, shoesmin, accmin, gender);
        return ResponseForm.success(POST_SUCCESS.getCode(), POST_SUCCESS.getMessage(), url);
    }
    /*
    @PostMapping(path = "/cody/imagemin", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseForm<List<String>> postminCody(
            Authentication authentication,
            @RequestPart(value = "topmin", required = false) MultipartFile topmin,
            @RequestPart(value = "bottommin", required = false) MultipartFile bottommin,
            @RequestPart(value = "shoesmin", required = false) MultipartFile shoesmin,
            @RequestPart(value = "accmin", required = false) MultipartFile accmin) {
        List<String> url = codyService.updateminCody(authentication.getName(), topmin, bottommin, shoesmin, accmin);
        return ResponseForm.success(POST_SUCCESS.getCode(), POST_SUCCESS.getMessage(), url);
    }
    */

    @PostMapping(path = "/cody/all", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseForm postCody(
            Authentication authentication,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        String url = codyService.updateallCody(authentication.getName(), file);
        return ResponseForm.success(POST_SUCCESS.getCode(), POST_SUCCESS.getMessage(), url);
    }

    @GetMapping("/cody")
    public ResponseForm<CodyResponseDto> getCody(Authentication authentication) {
        CodyResponseDto cody = null;
        cody = codyService.getCody(authentication.getName());

        CodyResponseDto codyResponseDto = codyService.getCody(authentication.getName());

        return ResponseForm.success(GET_CODY_SUCCESS.getCode(), GET_CODY_SUCCESS.getMessage(),
                cody);
    }

}



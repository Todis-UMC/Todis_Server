package com.todis.todisweb.demo.controller;


import static com.todis.todisweb.global.response.SuccessCode.GET_CODY_SUCCESS;
import static com.todis.todisweb.global.response.SuccessCode.POST_SUCCESS;

import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.dto.CodyImageDto;
import com.todis.todisweb.demo.dto.CodyResponseDto;
import com.todis.todisweb.demo.service.CodyServiceImpl;
import com.todis.todisweb.global.response.ResponseForm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
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

    @GetMapping("/cody/getall")
    public ResponseForm<CodyResponseDto> getallCody(Authentication authentication) {
        CodyResponseDto cody = null;
        cody = codyService.getallCody(authentication.getName());

        CodyResponseDto codyResponseDto = codyService.getallCody(authentication.getName());

        return ResponseForm.success(GET_CODY_SUCCESS.getCode(), GET_CODY_SUCCESS.getMessage(),
                cody);
    }

    @GetMapping("/cody/getimage")
    public ResponseForm<CodyImageDto> getCody(Authentication authentication) {
        CodyImageDto cody = null;
        cody = codyService.getImageCody(authentication.getName());

        CodyImageDto codyImageDto = codyService.getImageCody(authentication.getName());

        return ResponseForm.success(GET_CODY_SUCCESS.getCode(), GET_CODY_SUCCESS.getMessage(),
                cody);
    }

    @PutMapping("/cody/like")
    public ResponseForm likeCody(Authentication authentication, @RequestParam Integer codyId){
        String email = authentication.getName();
        codyService.likeCody(email, codyId);
        return ResponseForm.success(200, "성공", null);
    }

    @GetMapping("/cody/recommend")
    public ResponseForm<List<Cody>> getRecommendedCodies(){
        List<Cody> recommendedCodies = codyService.getTop7CodiesByLikes();
        return ResponseForm.success(200, "성공", recommendedCodies);
    }
}



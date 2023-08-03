package com.todis.todisweb.demo.controller;


import static com.todis.todisweb.global.response.SuccessCode.POST_SUCCESS;

import com.todis.todisweb.demo.dto.CodyDto;
import com.todis.todisweb.demo.service.CodyServiceImpl;
import com.todis.todisweb.global.response.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodyController {

    @Autowired
    private CodyServiceImpl codyService;

    @PostMapping("/cody/post")
    public ResponseForm postComment(Authentication authentication, String Comment) {
        codyService.updateComment(authentication.getName(),Comment);
        return ResponseForm.success(POST_SUCCESS.getCode(), POST_SUCCESS.getMessage(), null);
    }

}



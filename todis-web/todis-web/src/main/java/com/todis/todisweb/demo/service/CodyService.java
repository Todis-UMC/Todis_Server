package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.CodyDto;
import org.springframework.web.multipart.MultipartFile;

public interface CodyService {

    String updateComment(String email, String comment);
    String updateCody(String email, MultipartFile file);
}

package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.CodyDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CodyService {

    String updateComment(String email, String comment);

    List<String> updateCody(String email, MultipartFile top, MultipartFile bottom,
            MultipartFile shoes, MultipartFile acc);

    List<String> updateminCody(String email, MultipartFile topmin,
            MultipartFile bottommin, MultipartFile shoesmin, MultipartFile accmin);

    String updateallCody(String email, MultipartFile file);
}

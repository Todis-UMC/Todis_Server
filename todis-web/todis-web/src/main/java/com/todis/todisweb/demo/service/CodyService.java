package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.CodyImageDto;
import com.todis.todisweb.demo.dto.CodyResponseDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.todis.todisweb.demo.domain.Cody;

public interface CodyService {

    String updateComment(String email, String comment);

    List<String> updateCody(String email, MultipartFile top, MultipartFile bottom,
            MultipartFile shoes, MultipartFile acc, MultipartFile topmin,
            MultipartFile bottommin, MultipartFile shoesmin, MultipartFile accmin, Boolean gender);
/*
    List<String> updateminCody(String email, MultipartFile topmin,
            MultipartFile bottommin, MultipartFile shoesmin, MultipartFile accmin);
*/
    String updateallCody(String email, MultipartFile file);

    CodyResponseDto getallCody(String email);

    CodyImageDto getImageCody(String email);

    void likeCody(String email, Integer codyId);
    List<Cody> getTop7CodiesByLikes();
}

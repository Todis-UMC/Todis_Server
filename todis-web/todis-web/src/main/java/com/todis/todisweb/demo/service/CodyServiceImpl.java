package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.CodyDto;
import com.todis.todisweb.demo.repository.CodyRepository;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.s3.S3Uploader;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CodyServiceImpl implements CodyService{

    private final CodyRepository codyRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Autowired
    public CodyServiceImpl(S3Uploader s3Uploader, CodyRepository codyRepository, UserRepository userRepository) {
        this.s3Uploader = s3Uploader;
        this.codyRepository = codyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String updateComment(String email, String comment) {
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId(user.getId());

        Cody cody = selectedCody.get();
        cody.setComment(comment);
        codyRepository.save(cody);
        return "comment 업데이트";
    }

    @Override
    @Transactional
    public String updateCody(String email, MultipartFile file){
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId((user.getId()));

        String url = "";
        if(file != null) {
            url = s3Uploader.uploadImage(file);
        }
        Cody cody = selectedCody.get();
        cody.setImage(url);
        codyRepository.save(cody);
        return url;
    }
}

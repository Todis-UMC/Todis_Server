package com.todis.todisweb.demo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.CodyDto;
import com.todis.todisweb.demo.dto.CodyResponseDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import com.todis.todisweb.demo.repository.CodyRepository;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.demo.s3.S3Uploader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CodyServiceImpl implements CodyService {

    private final CodyRepository codyRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public CodyServiceImpl(S3Uploader s3Uploader, CodyRepository codyRepository,
            UserRepository userRepository, AmazonS3 amazonS3) {
        this.s3Uploader = s3Uploader;
        this.codyRepository = codyRepository;
        this.userRepository = userRepository;
        this.amazonS3 = amazonS3;
    }

    @Override
    public String updateComment(String email, String comment) {
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId(user.getId());

        if (selectedCody.isPresent()) {
            Cody cody = selectedCody.get();
            cody.setComment(comment);
            codyRepository.save(cody);
        } else {
            return "코멘트 없음";
        }

        return "comment 업데이트";
    }

    @Override
    @Transactional
    public List<String> updateCody(String email,  MultipartFile top,
            MultipartFile bottom, MultipartFile shoes, MultipartFile acc) {
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId((user.getId()));

        String topurl = "";
        String bottomurl = "";
        String shoesurl = "";
        String accurl = "";

        List<String> urlList = new ArrayList<>();

        if (top != null) {
            topurl = s3Uploader.uploadImage(top);
            urlList.add(topurl);
        }
        if (bottom != null) {
            bottomurl = s3Uploader.uploadImage(bottom);
            urlList.add(bottomurl);
        }
        if (shoes != null) {
            shoesurl = s3Uploader.uploadImage(shoes);
            urlList.add(shoesurl);
        }
        if (acc != null) {
            accurl = s3Uploader.uploadImage(acc);
            urlList.add(accurl);
        }


        Cody cody = selectedCody.get();
        cody.setTopimg(topurl);
        cody.setBottomimg(bottomurl);
        cody.setShoesimg(shoesurl);
        cody.setAccimg(accurl);


        codyRepository.save(cody);
        return urlList;
    }
    @Override
    @Transactional
    public List<String> updateminCody(String email, MultipartFile topmin,
            MultipartFile bottommin, MultipartFile shoesmin, MultipartFile accmin) {
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId((user.getId()));

        String topminurl = "";
        String bottomminurl = "";
        String shoesminurl = "";
        String accminurl = "";

        List<String> urlList = new ArrayList<>();
        ;

        if (topmin != null) {
            topminurl = s3Uploader.uploadImage(topmin);
            urlList.add(topminurl);
        }
        if (bottommin != null) {
            bottomminurl = s3Uploader.uploadImage(bottommin);
            urlList.add(bottomminurl);
        }
        if (shoesmin != null) {
            shoesminurl = s3Uploader.uploadImage(shoesmin);
            urlList.add(shoesminurl);
        }
        if (accmin != null) {
            accminurl = s3Uploader.uploadImage(accmin);
            urlList.add(accminurl);
        }

        Cody cody = selectedCody.get();

        cody.setTopminimg(topminurl);
        cody.setBottomminimg(bottomminurl);
        cody.setShoesminimg(shoesminurl);
        cody.setAccminimg(accminurl);

        codyRepository.save(cody);
        return urlList;
    }

    @Override
    @Transactional
    public String updateallCody(String email, MultipartFile file) {
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId((user.getId()));

        String codyurl = "";
        if (file != null) {
            codyurl = s3Uploader.uploadImage(file);
        }

        Cody cody = selectedCody.get();
        cody.setImage(codyurl);
        codyRepository.save(cody);
        return codyurl;
    }

    @Override
    public CodyResponseDto getCody(String email) {
        User user = userRepository.findByEmail(email);
        Boolean codyExists =  codyRepository.existsByUserId(user.getId());

        if (codyExists == true) {
            CodyResponseDto codyResponseDto = codyRepository.getCody(user.getId());
            return codyResponseDto;
        }else {
            Cody cody = new Cody();
            cody.setUserId(user.getId());
            cody.setGender(user.getGender());
            codyRepository.save(cody);

            CodyResponseDto codyResponseDto = new CodyResponseDto(user.getGender());
            return codyResponseDto;
        }


    }

}

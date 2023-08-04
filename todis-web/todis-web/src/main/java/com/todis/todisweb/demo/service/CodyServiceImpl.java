package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.CodyDto;
import com.todis.todisweb.demo.repository.CodyRepository;
import com.todis.todisweb.demo.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CodyServiceImpl implements CodyService{

    private final CodyRepository codyRepository;
    private final UserRepository userRepository;

    @Override
    public String updateComment(String email, String comment) {
        User user = userRepository.findByEmail(email);
        Optional<Cody> selectedCody = codyRepository.findByUserId(user.getId());

        Cody cody = selectedCody.get();
        cody.setComment(comment);
        codyRepository.save(cody);
        return "comment 업데이트";
    }

}

package com.todis.todisweb.demo.dao;

import com.todis.todisweb.demo.domain.QMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.querydsl.core.types.Predicate;

import java.util.Optional;

@SpringBootTest
public class QMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void queryDSLTest() {
        Predicate predicate = QMember.member.name.containsIgnoreCase("Catty");

        Optional<Member> foundMember = memberRepository.findOne(predicate);

        if (foundMember.isPresent()) {
            Member member = foundMember.get();
        }
    }
}

package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.Member;
import com.todis.todisweb.demo.domain.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberSupportRepositoryImpl extends QuerydslRepositorySupport implements
        MemberSupportRepository {

    public MemberSupportRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public List<Member> findByName(String name) {
        QMember member = QMember.member;

        List<Member> memberList = from(member)
                .where(member.name.eq(name))
                .select(member)
                .fetch();

        return memberList;
    }
}

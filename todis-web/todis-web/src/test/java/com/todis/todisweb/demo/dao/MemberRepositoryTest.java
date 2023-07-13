package com.todis.todisweb.demo.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.querydsl.jpa.impl.JPAQuery;
import com.todis.todisweb.demo.repository.MemberRepository;
import com.todis.todisweb.demo.domain.Member;
import com.todis.todisweb.demo.domain.QMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member();
        member.setName("Catty");

        Member savedMember = memberRepository.save(member);

        assertEquals(member.getName(), savedMember.getName());
    }

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void queryDslTest() {
        JPAQuery<Member> query = new JPAQuery(entityManager);
        QMember qProduct = QMember.member;

        List<Member> memberList = query
                .from(qProduct)
                .where(qProduct.name.eq("Catty"))
                .fetch();

        for (Member member : memberList) {
            System.out.println("----------------");
            System.out.println();
            System.out.println("Member id : " + member.getId());
            System.out.println("Member Name : " + member.getName());
            System.out.println();
            System.out.println("----------------");
        }
    }

//    @Autowired
//    JPAQueryFactory jpaQueryFactory;
//
//    @Test
//    void queryDslTest2(){
//        QMember qMember = QMember.member;
//
//        List<String> memberList = jpaQueryFactory
//                .select(qMember.name)
//                .from(qMember)
//                .fetch();
//
//        for (String member : memberList){
//            System.out.println("----------------");
//            System.out.println("Member Name : " + member);
//            System.out.println("----------------");
//        }
//    }
}
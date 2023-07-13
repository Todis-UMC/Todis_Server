package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.Member;

import java.util.List;

public interface MemberSupportRepository {

    List<Member> findByName(String name);
}

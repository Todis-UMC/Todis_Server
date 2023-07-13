package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.MemberReponseDto;
import com.todis.todisweb.demo.dto.MemberDto;

public interface MemberService {

    MemberReponseDto getMember(Long id);

    MemberReponseDto saveMember(MemberDto memberDto);

    MemberReponseDto changeMemberName(Long id, String name) throws Exception;

    void deleteMember(Long id) throws Exception;
}

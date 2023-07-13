package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.repository.MemberRepository;
import com.todis.todisweb.demo.domain.Member;
import com.todis.todisweb.demo.dto.MemberDto;
import com.todis.todisweb.demo.dto.MemberReponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberReponseDto getMember(Long id) {
        LOGGER.info("[getMember] input id : {}", id);
        Member member = memberRepository.findById(id).get();

        LOGGER.info("[getMember] member id : {}, name : {}", member.getId(), member.getName());
        MemberReponseDto memberReponseDto = new MemberReponseDto();
        memberReponseDto.setId(member.getId());
        memberReponseDto.setName(member.getName());

        return memberReponseDto;
    }

    @Override
    public MemberReponseDto saveMember(MemberDto memberDto) {
        LOGGER.info("[saveMember] MemberDto : {}", memberDto.toString());
        Member member = new Member();
        member.setName(memberDto.getName());

        Member savedMember = memberRepository.save(member);
        LOGGER.info("[saveMember] savedMember : {}", savedMember);

        MemberReponseDto memberReponseDto = new MemberReponseDto();
        memberReponseDto.setId(savedMember.getId());
        memberReponseDto.setName(savedMember.getName());

        return memberReponseDto;
    }

    @Override
    public MemberReponseDto changeMemberName(Long id, String name) throws Exception {
        Member foundMember = memberRepository.findById(id).get();
        foundMember.setName(name);
        Member changedMember = memberRepository.save(foundMember);

        MemberReponseDto memberReponseDto = new MemberReponseDto();
        memberReponseDto.setId(changedMember.getId());
        memberReponseDto.setName(changedMember.getName());

        return memberReponseDto;

    }

    @Override
    public void deleteMember(Long id) throws Exception {
        memberRepository.deleteById(id);
    }
}

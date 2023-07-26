package com.todis.todisweb.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class MemberServiceTest {

    private MemberRepository memberReposiory = Mockito.mock(MemberRepository.class);
    private MemberServiceImpl memberService;

    @BeforeEach
    public void setUpTest() {
        memberService = new MemberServiceImpl(memberReposiory);
    }

    @Test
    void getMemberTest() {
        Member givenMember = new Member();
        givenMember.setId(1234L);
        givenMember.setName("Catty");

        Mockito.when(memberReposiory.findById(123L))
                .thenReturn(Optional.of(givenMember));

        MemberReponseDto memberReponseDto = memberService.getMember(123L);

        Assertions.assertEquals(memberReponseDto.getId(), givenMember.getId());
        Assertions.assertEquals(memberReponseDto.getName(), givenMember.getName());

        verify(memberReposiory).findById(123L);
    }

    @Test
    void saveMemberTest() {
        Mockito.when(memberReposiory.save(any(Member.class)))
                .then(returnsFirstArg());

        MemberReponseDto memberReponseDto = memberService.saveMember(
                new MemberDto("Catty"));

        Assertions.assertEquals(memberReponseDto.getName(), "Catty");

        verify(memberReposiory).save(any());
    }
}

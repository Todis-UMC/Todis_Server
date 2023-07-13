package com.todis.todisweb.demo.controller;

import com.google.gson.Gson;
import com.todis.todisweb.demo.dto.MemberDto;
import com.todis.todisweb.demo.dto.MemberReponseDto;
import com.todis.todisweb.demo.service.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class memberContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MemberServiceImpl memberService;

    @Test
    @DisplayName("MockMvc를 통한 Member 데이터 가져오기 테스트")
    void getMemberTest() throws Exception {
        given(memberService.getMember(123L)).willReturn(
                new MemberReponseDto(123L, "todis"));

        String memberId = "123";

        mockMvc.perform(
                        get("/member?id=" + memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andDo(print());

        verify(memberService).getMember(123L);
    }

    @Test
    @DisplayName("Member 데이터 생성 테스트")
    void createMemberTest() throws Exception {
        given(memberService.saveMember(new MemberDto("catty")))
                .willReturn(new MemberReponseDto(1234L, "catty"));

        MemberDto memberDto = MemberDto.builder()
                .name("catty")
                .build();

        Gson gson = new Gson();
        String content = gson.toJson(memberDto);

        mockMvc.perform(
                        post("/member")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andDo(print());

        verify(memberService).saveMember(new MemberDto("catty"));
    }
}

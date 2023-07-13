package com.todis.todisweb.demo.controller;

import com.todis.todisweb.demo.dto.ChangeMemberNameDto;
import com.todis.todisweb.demo.dto.MemberDto;
import com.todis.todisweb.demo.dto.MemberReponseDto;
import com.todis.todisweb.demo.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/saved")
    public ResponseEntity<MemberReponseDto> createMember(@RequestBody MemberDto MemberDto) {
        MemberReponseDto memberReponseDto = memberService.saveMember(MemberDto);

        return ResponseEntity.status(HttpStatus.OK).body(memberReponseDto);
    }

//    @ApiOperation(value = "GET 메소드 예제", notes = "@RequestParam을 활용한 GET Method")
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<MemberReponseDto> getMember(@PathVariable Long id) {
        MemberReponseDto memberReponseDto = memberService.getMember(id);

        return ResponseEntity.status(HttpStatus.OK).body(memberReponseDto);
    }


    @PutMapping("/updated")
    public ResponseEntity<MemberReponseDto> changeMemberName(
            @RequestBody ChangeMemberNameDto changeMemberNameDto) throws Exception {
        MemberReponseDto memberReponseDto = memberService.changeMemberName(
                changeMemberNameDto.getId(),
                changeMemberNameDto.getName());

        return ResponseEntity.status(HttpStatus.OK).body(memberReponseDto);

    }

    @DeleteMapping(value = "/deleted/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) throws Exception {
        memberService.deleteMember(id);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}

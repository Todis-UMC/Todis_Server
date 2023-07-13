package com.todis.todisweb.demo.dto;

import lombok.*;

// 예제 6.20
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberReponseDto {

    private Long id;
    private String name;

}

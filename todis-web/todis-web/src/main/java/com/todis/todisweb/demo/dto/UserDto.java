package com.todis.todisweb.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class UserDto {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
}

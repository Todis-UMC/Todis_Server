package com.todis.todisweb.demo.dto;

import com.todis.todisweb.demo.domain.User;
import lombok.Getter;

@Getter
public class FriendListDto {

    private String name;
    private String email;
    private String profileImageUrl;

    public FriendListDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
    }

    public FriendListDto(String name, String email, String profileImageUrl) {
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }
}

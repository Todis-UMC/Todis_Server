package com.todis.todisweb.demo.dto;

import com.todis.todisweb.demo.domain.User;
import lombok.Getter;

@Getter
public class FriendListDto {

    private String name;
    private String profileImageUrl;

    public FriendListDto(User user) {
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
    }

    public FriendListDto(String name, String profileImageUrl) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}

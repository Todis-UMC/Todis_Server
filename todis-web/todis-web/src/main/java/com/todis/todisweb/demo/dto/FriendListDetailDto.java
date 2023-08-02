package com.todis.todisweb.demo.dto;

import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.domain.User;
import lombok.Getter;

@Getter
public class FriendListDetailDto {

    private String name;
    private String profileImageUrl;
    private String codyImage;
    private String comment;

    public FriendListDetailDto(User user, Cody cody) {
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
        this.codyImage = user.getCodyImage();
        this.comment = cody.getComment();
    }

    public FriendListDetailDto(String name, String profileImageUrl, String codyImage,
            String comment) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.codyImage = codyImage;
        this.comment = comment;
    }
}

package com.todis.todisweb.demo.dto;

import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.domain.FriendList;
import com.todis.todisweb.demo.domain.User;
import lombok.Getter;

@Getter
public class FriendListDetailDto {
//    private int id;
    private String name;
    private String profileImageUrl;
    private String codyImage;
    private String comment;

    public FriendListDetailDto(User user) {
//        this.id = id;
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
        this.codyImage = user.getCodyImage();
        this.comment = user.getComment();
    }

    public FriendListDetailDto(String name, String profileImageUrl, String codyImage,
            String comment) {
//        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.codyImage = codyImage;
        this.comment = comment;
    }
}

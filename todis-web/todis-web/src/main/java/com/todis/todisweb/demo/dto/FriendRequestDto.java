package com.todis.todisweb.demo.dto;

import com.todis.todisweb.demo.domain.FriendRequest;
import com.todis.todisweb.demo.domain.User;
import lombok.Getter;

@Getter
public class FriendRequestDto {

    private int request_id;
    private String name;
    private String profileImageUrl;

    public FriendRequestDto(User user, FriendRequest friendRequest){
        this.request_id = friendRequest.getId();
        this.name = user.getName();
        this.profileImageUrl = user.getProfileImageUrl();
    }

    public FriendRequestDto(int request_id, String name, String profileImageUrl){
        this.request_id = request_id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }
}

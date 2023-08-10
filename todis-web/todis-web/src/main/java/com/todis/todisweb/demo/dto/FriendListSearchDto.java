package com.todis.todisweb.demo.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class FriendListSearchDto {
    private long count;
    private List<FriendListDto> friendList;

    public FriendListSearchDto(long count, List<FriendListDto> friendListDto){
        this.count = count;
        this.friendList = friendListDto;
    }

}

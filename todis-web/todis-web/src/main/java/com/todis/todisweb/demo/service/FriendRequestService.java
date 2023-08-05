package com.todis.todisweb.demo.service;


import com.todis.todisweb.demo.dto.FriendRequestDto;
import java.util.List;

public interface FriendRequestService {
    void FriendRequestUserIdToFriendId(String user_email, String friend_email);

    void FriendRequestAcceptUser(long request_id);

    void FriendRequestAcceptFriend(long request_id);

    void FriendRequestDelete(long request_id);

    List<FriendRequestDto> friendRequestList(String user_email);
}

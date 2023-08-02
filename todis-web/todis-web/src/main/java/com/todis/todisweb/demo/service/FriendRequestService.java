package com.todis.todisweb.demo.service;


public interface FriendRequestService {
    void FriendRequestUserIdToFriendId(String user_email, String friend_email);

    void FriendRequestAcceptUser(long request_id);

    void FriendRequestAcceptFriend(long request_id);

    void FriendRequestDelete(long request_id);
}

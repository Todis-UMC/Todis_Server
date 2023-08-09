package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import java.util.List;

public interface FriendListService {
    List<FriendListDto> findFriendListByUserId(String user_email);

    List<FriendListDetailDto> findFriendListByUserIdDetail(String user_email);

    void deleteFriendlist(String user_email, String friend_email);

    List<FriendListDto> searchFriendList(String user_email, String keyword);
}

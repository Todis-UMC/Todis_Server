package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import com.todis.todisweb.demo.dto.FriendListSearchDto;
import java.util.List;

public interface FriendListService {
    List<FriendListDto> findFriendListByUserId(String user_email);

    List<FriendListDetailDto> findFriendListByUserIdDetail(String user_email, int id);

    void deleteFriendlist(String user_email, String friend_email);

    FriendListSearchDto searchFriendList(String user_email, String keyword);
}

package com.todis.todisweb.demo.controller;

import static com.todis.todisweb.global.response.SuccessCode.*;

import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import com.todis.todisweb.demo.dto.FriendListSearchDto;
import com.todis.todisweb.demo.service.FriendListService;
import com.todis.todisweb.global.response.ResponseForm;
import jakarta.annotation.Nullable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendListController {

    @Autowired
    private FriendListService friendListService;

    @GetMapping("/list")
    public ResponseForm<List<FriendListDto>> getFriendList(Authentication authentication) {
        List<FriendListDto> friendList = null;
        friendList = friendListService.findFriendListByUserId(authentication.getName());
        return ResponseForm.success(GET_FRIEND_LIST.getCode(), GET_FRIEND_LIST.getMessage(),
                friendList);
    }

    @GetMapping("/listdetail")
    public ResponseForm<List<FriendListDetailDto>> getFriendListDetail(
            Authentication authentication) {
        List<FriendListDetailDto> friendListDetail = null;
        friendListDetail = friendListService.findFriendListByUserIdDetail(authentication.getName());
        return ResponseForm.success(GET_FRIEND_LIST_DETAIL.getCode(),
                GET_FRIEND_LIST_DETAIL.getMessage(), friendListDetail);
    }

    @DeleteMapping("/listdelete")
    public ResponseForm deleteFriend(Authentication authentication, String friend_email) {
        friendListService.deleteFriendlist(authentication.getName(), friend_email);
        return ResponseForm.success(DELETE_FRIEND_LIST.getCode(), DELETE_FRIEND_LIST.getMessage(),
                null);
    }


    @GetMapping("/listsearch")
    public ResponseForm<FriendListSearchDto> getSearchFriendList(Authentication authentication,
            @Nullable String keyword) {
        FriendListSearchDto searchFriendList = null;
        searchFriendList = friendListService.searchFriendList(authentication.getName(), keyword);
        return ResponseForm.success(GET_SEARCH_FRIEND_LIST.getCode(),
                GET_SEARCH_FRIEND_LIST.getMessage(), searchFriendList);
    }

    @GetMapping("/listuser")
    public ResponseForm<FriendListDetailDto> getUserinfoInFriendList(
            Authentication authentication) {
        FriendListDetailDto userInfo = null;
        userInfo = friendListService.UserInfoInFriendList(authentication.getName());
        return ResponseForm.success(GET_USERINFO_IN_FRIENDLIST.getCode(),
                GET_USERINFO_IN_FRIENDLIST.getMessage(), userInfo);
    }
}

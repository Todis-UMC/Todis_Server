package com.todis.todisweb.demo.controller;

import static com.todis.todisweb.global.response.SuccessCode.*;

import com.todis.todisweb.demo.service.FriendRequestService;
import com.todis.todisweb.demo.service.FriendRequestServiceImpl;
import com.todis.todisweb.global.response.ResponseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("/friend")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping("/request")
    public ResponseForm requestFriend(Authentication authentication, String friend_email){
        friendRequestService.FriendRequestUserIdToFriendId(authentication.getName(), friend_email);
        return ResponseForm.success(FRIEND_REQUEST.getCode(), FRIEND_REQUEST.getMessage(), null);
    }

    @PutMapping("/accept")
    public ResponseForm requestFriendAccept(int request_id){
        friendRequestService.FriendRequestAcceptUser(request_id);
        friendRequestService.FriendRequestAcceptFriend(request_id);
        return ResponseForm.success(ACCEPT_FRIEND_REQUEST.getCode(), ACCEPT_FRIEND_REQUEST.getMessage(), null);
    }

    @DeleteMapping("/delete")
    public ResponseForm requestFriendDelete(int request_id){
        friendRequestService.FriendRequestDelete(request_id);
        return ResponseForm.success(DELETE_FRIEND_REQUEST.getCode(), DELETE_FRIEND_REQUEST.getMessage(), null);
    }

}

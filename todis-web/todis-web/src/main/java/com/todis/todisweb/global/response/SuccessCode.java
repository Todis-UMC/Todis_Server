package com.todis.todisweb.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    JOIN_SUCCESS(200, "Join success!"),
    LOGIN_SUCCESS(200, "Login success!"),
    FIND_PASSWORD(200, "Generate Temporary Password!"),
    CHANGE_PASSWORD(200, "Change password!"),
    CHANGE_NAME(200, "Change Name!"),
    LEAVE_USER(200, "Leave use!"),
    KAKAO_LOGIN(200, "Kakao login!"),
    GOOGLE_LOGIN(200, "Google login!"),
    GET_USER_INFO(200, "Find it!"),
    MATCHED_PASSWORD(200, "Valid password!"),



    //FriendRequest
    FRIEND_REQUEST(200, "Friend Request!"),
    ACCEPT_FRIEND_REQUEST(200, "Friend Request Accept!"),
    DELETE_FRIEND_REQUEST(200, "Friend Request Delete!"),
    GET_FREIND_REQUEST_LIST(200, "Get Friend Request List!"),

    //FreindList
    GET_FRIEND_LIST(200, "Get Friend List!"),
    GET_FRIEND_LIST_DETAIL(200, "Get Friend List Detail!"),
    DELETE_FRIEND_LIST(200, "Delete Friend List!"),
    GET_SEARCH_FRIEND_LIST(200, "Get Search Friend List!"),
    GET_USERINFO_IN_FRIENDLIST(200, "Get User Info In Friend List!"),

    //Cody
    POST_SUCCESS(200, "Post success!"),
    GET_CODY_SUCCESS(200,"Cody Accept!")
    ;
    // 다른 성공 코드들...

    private final int code;
    private final String message;

    }


package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.domain.FriendList;
import com.todis.todisweb.demo.domain.FriendRequest;
import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.FriendRequestDto;
import com.todis.todisweb.demo.repository.FriendListRepository;
import com.todis.todisweb.demo.repository.FriendRequestRepository;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.global.exception.ServiceException;
import com.todis.todisweb.global.response.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private FriendListRepository friendListRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void FriendRequestUserIdToFriendId(String user_email, String friend_email) {

        if (!userRepository.existsByEmail(friend_email)){
            throw new ServiceException(ErrorCode.USER_NOT_EXISTS);
        }

        int user_id = userRepository.findByEmail(user_email).getId();
        int friend_id = userRepository.findByEmail(friend_email).getId();

        if (friendRequestRepository.existsByUserIdAndFriendId(user_id, friend_id)) {
            throw new ServiceException(ErrorCode.REQUEST_ALREADY_EXISTS);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setUserId(user_id);
        friendRequest.setFriendId(friend_id);
        friendRequest.setIsAllowed(false);

        FriendRequest savedFriendRequest = friendRequestRepository.save(friendRequest);
    }

    @Override
    public void FriendRequestAcceptUser(long request_id) {
        Optional<FriendRequest> findFriendRequest = friendRequestRepository.findById(request_id);

        if (!findFriendRequest.isPresent()) {
            throw new ServiceException(ErrorCode.REQUEST_NOT_EXISTS);
        }

        FriendRequest friendRequest = findFriendRequest.get();

        if (friendRequest.getIsAllowed() == true) {
            throw new ServiceException(ErrorCode.REQUEST_ALREADY_ACCEPTED);
        }

        int user_id = friendRequest.getUserId();
        int friend_id = friendRequest.getFriendId();

        FriendList friendList = new FriendList();
        friendList.setUserId(user_id);
        friendList.setFriendId(friend_id);

        friendListRepository.save(friendList);

    }

    @Override
    public void FriendRequestAcceptFriend(long request_id) {
        Optional<FriendRequest> findFriendRequest = friendRequestRepository.findById(request_id);

        if (!findFriendRequest.isPresent()) {
            throw new ServiceException(ErrorCode.REQUEST_NOT_EXISTS);
        }

        FriendRequest friendRequest = findFriendRequest.get();

        if (friendRequest.getIsAllowed() == true) {
            throw new ServiceException(ErrorCode.REQUEST_ALREADY_ACCEPTED);
        }

        int user_id = friendRequest.getUserId();
        int friend_id = friendRequest.getFriendId();

        friendRequest.setIsAllowed(true);

        FriendList friendList = new FriendList();
        friendList.setUserId(friend_id);
        friendList.setFriendId(user_id);

        friendListRepository.save(friendList);

    }


    public void FriendRequestDelete(long request_id) {
        Optional<FriendRequest> findFriendRequest = friendRequestRepository.findById(request_id);

        if (!findFriendRequest.isPresent()) {
            throw new ServiceException(ErrorCode.REQUEST_NOT_EXISTS);
        }

        friendRequestRepository.deleteById(request_id);

    }

    public List<FriendRequestDto> friendRequestList(String user_email) {
        List<FriendRequestDto> result = null;
        User user = userRepository.findByEmail(user_email);
        result = friendRequestRepository.findByFriendId(user.getId());

        return result;
    }
}

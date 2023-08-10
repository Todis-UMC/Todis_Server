package com.todis.todisweb.demo.service;

import com.todis.todisweb.demo.domain.User;
import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import com.todis.todisweb.demo.dto.FriendListSearchDto;
import com.todis.todisweb.demo.repository.FriendListRepository;
import com.todis.todisweb.demo.repository.FriendListRepositorySupport;
import com.todis.todisweb.demo.repository.UserRepository;
import com.todis.todisweb.global.exception.ServiceException;
import com.todis.todisweb.global.response.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FriendListServiceImpl implements FriendListService{

    @Autowired
    private FriendListRepository friendListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendListRepositorySupport friendListRepositorySupport;

    @Override
    public List<FriendListDto> findFriendListByUserId(String user_email) {
        List<FriendListDto> result = null;
        User user = userRepository.findByEmail(user_email);
        result = friendListRepository.findFriendIdByUserId(user.getId());

        return result;
    }

    @Override
    public List<FriendListDetailDto> findFriendListByUserIdDetail(String user_email, int id){
        List<FriendListDetailDto> result = null;
        User user = userRepository.findByEmail(user_email);
        result = friendListRepositorySupport.findFriendIdByUserIdDetail(user.getId(), id);
        return result;
    }

    @Override
    public void deleteFriendlist(String user_email, String friend_email){
        int user_id = userRepository.findByEmail(user_email).getId();
        int friend_id = userRepository.findByEmail(friend_email).getId();
        if (!friendListRepository.existsByUserIdAndFriendId(user_id, friend_id)){
            throw new ServiceException(ErrorCode.ALREADY_EXISTS);
        }
        friendListRepository.deleteByUserIdAndFriendId(user_id, friend_id);
        friendListRepository.deleteByUserIdAndFriendId(friend_id, user_id);
    }

    @Override
    public FriendListSearchDto searchFriendList(String user_email, String keyword){
        int user_id = userRepository.findByEmail(user_email).getId();
        List<FriendListDto> searchList = null;
        if(keyword==null){
            searchList = friendListRepository.findFriendIdByUserId(user_id);
        } else{
            searchList = friendListRepositorySupport.searchFriendList(user_id, keyword);
        }
        long count = friendListRepository.countFriendListByUserId(user_id);
        FriendListSearchDto friendListSearchDto = new FriendListSearchDto(count, searchList);
        return friendListSearchDto;
    }
}

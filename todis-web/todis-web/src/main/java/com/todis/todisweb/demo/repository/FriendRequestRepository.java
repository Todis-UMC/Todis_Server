package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

boolean existsByUserIdAndFriendId(int user_id, int friend_id);



}

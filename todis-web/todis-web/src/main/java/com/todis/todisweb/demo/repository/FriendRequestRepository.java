package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.FriendRequest;
import com.todis.todisweb.demo.dto.FriendListDto;
import com.todis.todisweb.demo.dto.FriendRequestDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

boolean existsByUserIdAndFriendId(int user_id, int friend_id);

@Query("select new com.todis.todisweb.demo.dto.FriendRequestDto(fr.id, u.name, u.profileImageUrl) from User u, FriendRequest fr where fr.userId = u.id and fr.friendId = :userId and fr.isAllowed = false")
List<FriendRequestDto> findByFriendId(@Param("userId") int user_id);

}

package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.FriendList;
import com.todis.todisweb.demo.dto.FriendListDetailDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import jakarta.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Long> {

    Boolean existsByUserIdAndFriendId(int user_id, int friend_id);
    List<FriendList> findByUserIdAndFriendId(int user_id, int friend_id);

    void deleteByUserIdAndFriendId(int user_id, int friend_id);

    @Query("select new com.todis.todisweb.demo.dto.FriendListDto(u.name, u.email, u.profileImageUrl) from User u where u.id in (select fl.friendId from FriendList fl where fl.userId = :userId)")
    List<FriendListDto> findFriendIdByUserId(@Param("userId") int user_id);

    @Query(value = "insert into Friend_list (user_id,friend_id) VALUES (:userId,:friendId)", nativeQuery = true)
    @Modifying
    @Transactional
    void acceptFriendList(@Param("userId") int user_id,@Param("friendId") int friend_id);

    long countFriendListByUserId(int user_id);

    @Query("select new com.todis.todisweb.demo.dto.FriendListDetailDto(u.name, u.profileImageUrl, u.codyImage, u.comment) from User u where u.id = :user_id")
    FriendListDetailDto getUserInfo(int user_id);

    @Query(value = "select new com.todis.todisweb.demo.dto.FriendListDetailDto(u.name, u.profileImageUrl, u.codyImage, u.comment) from User u where u.id in (select fl.friendId from FriendList fl where fl.userId = :userId)")
    List<FriendListDetailDto> findFriendIdByUserIdDetail(@Param("userId") int user_id);
}

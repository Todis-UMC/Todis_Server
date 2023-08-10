package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.Cody;
import com.todis.todisweb.demo.dto.CodyResponseDto;
import com.todis.todisweb.demo.dto.FriendListDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodyRepository extends JpaRepository<Cody, Long> {

    Optional<Cody> findByUserId(int userid);

    Boolean existsByUserId(int userid);

    /*
        @Query("select new com.todis.todisweb.demo.dto.CodyResponseDto(c.topimg, c.bottomimg, c.shoesimg, c.accimg, c.topminimg, c.bottomminimg, c.shoesminimg, c.accminimg, c.gender) from Cody c where c.userId = :userId")
        CodyResponseDto getCody(@Param("userId") int user_id);
     */
    @Query("select new com.todis.todisweb.demo.dto.CodyResponseDto(c.topimg, c.bottomimg, c.shoesimg, c.accimg, c.topminimg, c.bottomminimg, c.shoesminimg, c.accminimg) from Cody c where c.userId = :userId")
    CodyResponseDto getCody(@Param("userId") int user_id);
}

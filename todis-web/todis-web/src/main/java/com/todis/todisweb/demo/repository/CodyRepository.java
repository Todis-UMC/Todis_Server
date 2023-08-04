package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.Cody;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodyRepository extends JpaRepository<Cody,Long> {

    Optional<Cody> findByUserId(int userid) ;

}

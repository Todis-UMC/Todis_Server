package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.LikeCody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeCodyRepository extends JpaRepository<LikeCody, Long> {

}

package com.todis.todisweb.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.todis.todisweb.demo.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
        QuerydslPredicateExecutor<Member>
        , MemberSupportRepository {

    Optional<Member> findById(Long id);

    List<Member> findAllByName(String name);

    Member queryById(Long id);

    @Query("SELECT m FROM Member m WHERE m.name = :name")
    List<Member> findByNameParam(@Param("name") String name);
}

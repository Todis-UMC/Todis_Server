package com.todis.todisweb.demo.repository;

import com.todis.todisweb.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

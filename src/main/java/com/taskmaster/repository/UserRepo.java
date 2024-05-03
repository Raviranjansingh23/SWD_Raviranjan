package com.taskmaster.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.taskmaster.entity.AppUser;

@Repository
@Transactional(readOnly = true)
public interface UserRepo extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByEmail(String email);

	AppUser findByUsername(String username);

}

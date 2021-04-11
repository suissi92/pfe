package com.app.cms2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
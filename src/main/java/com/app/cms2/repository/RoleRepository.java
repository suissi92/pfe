package com.app.cms2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.com.cms2.model.Role;
import app.com.cms2.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	 Optional<Role> findByName(RoleName roleName);
}
package com.poly.DATN_BookWorms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Roles;

public interface RoleRepo extends JpaRepository<Roles, String> {

	@Query("SELECT o FROM Roles o WHERE o.rolename = ?1")
	Roles findByName(String string);

	
}

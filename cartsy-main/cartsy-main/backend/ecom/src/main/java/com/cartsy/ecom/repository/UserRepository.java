package com.cartsy.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cartsy.ecom.api.v1.model.EcomUser;

public interface UserRepository extends JpaRepository<EcomUser, Integer>{
	
	@Query("SELECT u FROM EcomUser u WHERE u.ecom_username = :username")
	List<EcomUser> findByUsername(@Param("username") String username);

}

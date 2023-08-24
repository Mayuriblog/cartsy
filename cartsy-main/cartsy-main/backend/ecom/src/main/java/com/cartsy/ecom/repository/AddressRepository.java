package com.cartsy.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cartsy.ecom.api.v1.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	@Query("SELECT a FROM Address a WHERE a.ecomUserId = :id")
	List<Address> findByEcomUserId(@Param("id") Integer id);


}


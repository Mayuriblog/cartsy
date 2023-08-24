package com.cartsy.ecom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cartsy.ecom.api.v1.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{



	@Query("SELECT o FROM Order o WHERE o.ecomUserId = :id")
	List<Order> byBuyer(@Param("id") Integer id);

	@Query("SELECT o FROM Order o WHERE o.dateOfOrder >= :creationDate")
	List<Order> filterByCreationDate(@Param("creationDate") Date creationDate);




}
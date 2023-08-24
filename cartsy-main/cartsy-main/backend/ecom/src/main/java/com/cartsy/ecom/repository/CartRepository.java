package com.cartsy.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cartsy.ecom.api.v1.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {


	

	

}

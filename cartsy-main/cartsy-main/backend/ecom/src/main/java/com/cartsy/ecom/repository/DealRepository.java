package com.cartsy.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cartsy.ecom.api.v1.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Integer> {

}

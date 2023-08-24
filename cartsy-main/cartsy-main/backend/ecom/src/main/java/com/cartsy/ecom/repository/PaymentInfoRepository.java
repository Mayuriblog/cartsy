package com.cartsy.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cartsy.ecom.api.v1.model.PaymentInfo;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Integer>{
	@Query("SELECT p FROM PaymentInfo p WHERE p.ecomUserId = :id")
	List<PaymentInfo> findByEcomUserId(@Param("id") Integer id);

}

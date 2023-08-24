package com.cartsy.ecom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cartsy.ecom.api.v1.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
	
	@Query("SELECT s FROM Seller s WHERE s.sellerName LIKE %:text%")
	List<Seller> search(@Param("text") String searchText );
	
	@Query("SELECT s FROM Seller s WHERE s.sellerDoj >= :creationDate")
	List<Seller> filterByCreationDate(@Param("creationDate") Date creationDate);

}

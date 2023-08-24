package com.cartsy.ecom.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cartsy.ecom.api.v1.model.Buyer;
import com.cartsy.ecom.api.v1.model.Product;

public interface BuyerRepository extends JpaRepository<Buyer, Integer>{
	@Query("SELECT b FROM Buyer b WHERE b.buyerName LIKE %:text%")
	List<Buyer> search(@Param("text") String searchText );
	
	@Query("SELECT b FROM Buyer b WHERE b.buyerDoj >= :creationDate")
	List<Buyer> filterByCreationDate(@Param("creationDate") Date creationDate);

	@Query("SELECT b FROM Buyer b WHERE b.ecomUserId = :id")
	List<Buyer> findByEcomUserId(@Param("id") Integer id);

}

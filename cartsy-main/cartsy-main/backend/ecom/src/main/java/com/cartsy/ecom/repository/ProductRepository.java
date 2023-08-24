package com.cartsy.ecom.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cartsy.ecom.api.v1.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	@Query("SELECT p FROM Product p WHERE p.productName LIKE %:text% OR p.productSDesc LIKE %:text% OR p.productLDesc LIKE %:text% OR p.brand LIKE %:text%")
	List<Product> search(@Param("text") String searchText );

	@Query("SELECT p FROM Product p WHERE p.categoryId = :category")
	List<Product> recommendation(@Param("category") Integer category);
	
	@Query("SELECT p FROM Product p WHERE p.categoryId = :category")
	List<Product> filterByCategory(@Param("category") Integer category);
	
	@Query("SELECT p FROM Product p WHERE p.sellerId = :sellerId")
	List<Product> filterBySeller(@Param("sellerId") Integer sellerId);
	
	@Query("SELECT p FROM Product p WHERE p.createdDate >= :creationDate")
	List<Product> filterByCreationDate(@Param("creationDate") Date creationDate);
	
	List<Product> findByIdIn(@Param("ids") int[] ids);

	@Query("SELECT p FROM Product p ORDER BY p.createdDate DESC")
	List<Product> filterByLatest();

	@Query("SELECT p FROM Product p ORDER BY p.orderCount DESC")
	List<Product> filterByBestseller();

	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.orderCount = p.orderCount + 1 WHERE p.id = :id")
	void updateOrderCount(@Param("id") Integer id);

	
}
package com.ty.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ty.shoppingcart.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE p.cost BETWEEN :value1 AND :value2")
	List<Product> getProductsByCost(@Param("value1")double value1 ,@Param("value2") double value2);
}

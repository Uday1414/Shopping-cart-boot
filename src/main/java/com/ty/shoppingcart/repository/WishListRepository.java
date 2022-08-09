package com.ty.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.shoppingcart.dto.WishList;

public interface WishListRepository extends JpaRepository<WishList, Integer> {

}

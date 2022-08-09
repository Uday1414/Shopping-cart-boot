package com.ty.shoppingcart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ty.shoppingcart.dto.WishList;
import com.ty.shoppingcart.repository.WishListRepository;

@Repository
public class WishListDao {
	
	@Autowired
	WishListRepository wishListRepository;

	public WishList saveWishList(WishList wishList) {

		return wishListRepository.save(wishList);
	}

	public WishList getWishList(int id) {

		Optional<WishList> optional = wishListRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public List<WishList> getAllWishList() {
		return wishListRepository.findAll();
	}

	public WishList updateWishList(int id, WishList wishList) {
		Optional<WishList> optional = wishListRepository.findById(id);
		if (optional.isPresent()) {
			return wishListRepository.save(wishList);
		} else {
			return null;
		}
	}

	public boolean deleteWishList(int id) {
		Optional<WishList> optional = wishListRepository.findById(id);
		if (optional.isPresent()) {
			wishListRepository.delete(optional.get());
			return true;
		} else {
			return false;
		}
	}
}

package com.ty.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ty.shoppingcart.dto.ResponseStructure;
import com.ty.shoppingcart.dto.WishList;
import com.ty.shoppingcart.service.WishListService;

@RestController
public class WishListController {
	
	@Autowired
	WishListService wishListService;
	
	@PostMapping("/wishLists/add/{cid}/{pid}")
	public ResponseStructure<WishList> addProductsToWishList(@PathVariable int cid, @PathVariable int pid) {

		return wishListService.addProductsToWishList(cid,pid);
	}

	@GetMapping("/wishLists/{id}")
	public ResponseStructure<WishList> getWishList(@PathVariable int id) {
		return wishListService.getWishList(id);
	}
	
	@GetMapping("/wishLists")
	public ResponseStructure<List<WishList>> getAllWishList(){
		return wishListService.getAllWishList();
	}

	@PutMapping("/wishLists/{id}")
	public ResponseStructure<WishList> updateWishList(@PathVariable int id, @RequestBody WishList wishList) {
		return wishListService.updateWishList(id, wishList);
	}

	@DeleteMapping("/wishLists/{id}")
	public ResponseStructure<String> deleteWishList(@PathVariable int id) {
		return wishListService.deleteWishList(id);
	}
	
	@DeleteMapping("/wishLists/product/{cid}/{pid}")
	public ResponseStructure<WishList> deleteProductFromWishList(@PathVariable int cid,@PathVariable int pid) {
		return wishListService.deleteProductFromWishList(cid,pid);
	}
}

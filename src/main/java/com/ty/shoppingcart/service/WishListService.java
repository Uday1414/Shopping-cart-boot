package com.ty.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ty.shoppingcart.dao.CustomerDao;
import com.ty.shoppingcart.dao.ProductDao;
import com.ty.shoppingcart.dao.WishListDao;
import com.ty.shoppingcart.dto.Customer;
import com.ty.shoppingcart.dto.Product;
import com.ty.shoppingcart.dto.ResponseStructure;
import com.ty.shoppingcart.dto.WishList;
import com.ty.shoppingcart.exception.NoIdFoundException;

@Service
public class WishListService {
	@Autowired
	CustomerDao customerDao;
	@Autowired
	WishListDao wishListDao;
	@Autowired
	ProductDao productDao;

	public ResponseStructure<WishList> addProductsToWishList(int cid, int pid) {
		ResponseStructure<WishList> responseStructure = new ResponseStructure<WishList>();
		Customer customer = customerDao.getCustomer(cid);
		WishList wishList = customer.getWishList();
		Product product = productDao.getProduct(pid);
		if (product != null) {
			if (wishList.getProducts() == null) {
				List<Product> products = new ArrayList<>();
				products.add(product);
				wishList.setProducts(products);
				WishList wishList1 = wishListDao.saveWishList(wishList);
				if (wishList1 != null) {
					responseStructure.setData(wishList1);
					responseStructure.setStatusCode(HttpStatus.CREATED.value());
					responseStructure.setMessage("Success");
				} else {
					throw new NoIdFoundException("Not Saved");
				}
			} else {
				List<Product> products = wishList.getProducts();
				products.add(product);
				wishList.setProducts(products);
				WishList wishList1 = wishListDao.saveWishList(wishList);
				if (wishList1 != null) {
					responseStructure.setData(wishList1);
					responseStructure.setStatusCode(HttpStatus.CREATED.value());
					responseStructure.setMessage("Success");
				} else {
					throw new NoIdFoundException("Not Saved");
				}
			}
		} else {
			throw new NoIdFoundException("no product with given id");
		}

		return responseStructure;

	}

	public ResponseStructure<WishList> getWishList(int id) {

		ResponseStructure<WishList> responseStructure = new ResponseStructure<WishList>();
		WishList wishList = wishListDao.getWishList(id);
		if (wishList != null) {
			responseStructure.setData(wishList);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Success");
		} else {
			throw new NoIdFoundException();
		}
		return responseStructure;
	}

	public ResponseStructure<List<WishList>> getAllWishList() {
		ResponseStructure<List<WishList>> responseStructure = new ResponseStructure<List<WishList>>();
		List<WishList> wishListes = wishListDao.getAllWishList();
		if (wishListes.size() > 0) {
			responseStructure.setData(wishListes);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Success");
		} else {
			throw new NoIdFoundException();
		}
		return responseStructure;
	}

	public ResponseStructure<WishList> updateWishList(int id, WishList wishList) {
		ResponseStructure<WishList> responseStructure = new ResponseStructure<WishList>();
		WishList wishList1 = wishListDao.updateWishList(id, wishList);
		if (wishList1 != null) {
			responseStructure.setData(wishList1);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Updated");
		} else {
			throw new NoIdFoundException();
		}
		return responseStructure;
	}

	public ResponseStructure<String> deleteWishList(int id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		boolean flag = wishListDao.deleteWishList(id);
		if (flag) {
			responseStructure.setData("Data deleted");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Deleted");
		} else {
			throw new NoIdFoundException("WishList id " + id + " Does not exist");
		}
		return responseStructure;
	}

	public ResponseStructure<WishList> deleteProductFromWishList(int cid, int pid) {
		ResponseStructure<WishList> responseStructure = new ResponseStructure<WishList>();
		Customer customer = customerDao.getCustomer(cid);
		if (customer != null) {
			WishList wishList = customer.getWishList();
			List<Product> products = new ArrayList<>();
			for (Product product2 : wishList.getProducts()) {
				if (product2.getId() != pid) {
					products.add(product2);
				}
			}
			wishList.setProducts(products);
			WishList wishList1 = wishListDao.updateWishList(wishList.getId(), wishList);
			if (wishList1 != null) {
				responseStructure.setData(wishList1);
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Updated");
			} else {
				throw new NoIdFoundException();
			}
		} else {
			throw new NoIdFoundException("No Customer with given id");
		}
		return responseStructure;
	}
}

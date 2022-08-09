package com.ty.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.shoppingcart.dto.Address;
import com.ty.shoppingcart.dto.Customer;
import com.ty.shoppingcart.dto.Login;
import com.ty.shoppingcart.dto.Product;
import com.ty.shoppingcart.dto.ResponseStructure;
import com.ty.shoppingcart.exception.NoIdFoundException;
import com.ty.shoppingcart.service.CustomerService;
import com.ty.shoppingcart.service.MailSenderService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;

	@Autowired
	MailSenderService mailSenderService;

	Customer customer2;

	@PostMapping("/customers")
	public ResponseStructure<String> registerCustomer(@RequestBody Customer customer) {

		customer2 = customer;
		customerService.saveCustomer(customer);
		return mailSenderService.sendMail(customer);
	}

	@GetMapping("/customers/confirm")
	public ResponseStructure<Customer> saveCustomer(@RequestBody Customer customer, @RequestParam String token) {
		boolean flag = customerService.checkToken(customer2, token);
		if (flag) {
			customer2.setPassword(customer.getPassword());
			return customerService.updateCustomer(customer2.getId(),customer2);
		} else {
			throw new NoIdFoundException("Token does not match");
		}
	}

	@GetMapping("/customers/{id}")
	public ResponseStructure<Customer> getCustomer(@PathVariable int id) {
		return customerService.getCustomer(id);
	}
	
	@GetMapping("/customers/address/{id}")
	public ResponseStructure<List<Address>> getAllCustomerAdddresses(@PathVariable int id) {
		return customerService.getAllCustomerAdddresses(id);
	}

	@PutMapping("/customers/{id}")
	public ResponseStructure<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
		return customerService.updateCustomer(id, customer);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseStructure<String> deleteCustomer(@PathVariable int id) {
		return customerService.deleteCustomer(id);
	}

	@PostMapping("/customers/login")
	public ResponseStructure<Customer> validateCustomer(@RequestBody Login login) {
		return customerService.validateCustomer(login.getEmail(), login.getPassword());
	}
	
	@GetMapping("/customers/products/view")
	public ResponseStructure<List<Product>> viewAllProducts(){
		return customerService.viewAllProducts();
	}
	
	@GetMapping("/customers/products/view/brand/{brand}")
	public ResponseStructure<List<Product>> viewProductByBrand(@PathVariable String brand){
		return customerService.viewProductByBrand(brand);
	}
	
	@GetMapping("/customers/products/view/type/{type}")
	public ResponseStructure<List<Product>> viewProductByType(@PathVariable String type){
		return customerService.viewProductByType(type);
	}
	
	@GetMapping("/customers/products/view/name/{name}")
	public ResponseStructure<List<Product>> viewProductByName(@PathVariable String name){
		return customerService.viewProductByName(name);
	}
	
	@GetMapping("/customers/products/view/cost/{cost}")
	public ResponseStructure<List<Product>> viewProductByCost(@PathVariable double cost){
		return customerService.viewProductByCost(cost);
	}
	
	@GetMapping("/customers/products/view/cost/{low}/{high}")
	public ResponseStructure<List<Product>> viewProductByCostRange(@PathVariable double low ,@PathVariable double high ){
		return customerService.viewProductByCostRange(low, high);
	}
}

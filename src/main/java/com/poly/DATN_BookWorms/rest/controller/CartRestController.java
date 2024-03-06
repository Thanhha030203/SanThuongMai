package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.service.CartService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/cart")
public class CartRestController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	HttpServletRequest rquest;
	
	@Autowired
	CRC32_SHA256 crc;
	
	@GetMapping
	public List<Cart> selectAllCart(){ 
		return cartService.findAll();
	}
	@GetMapping("/user")
	public List<Cart> selectUserCart(){ 
		return cartService.findByUser();
	}

	
	@GetMapping("/{cartid}")
	public Cart SelectById(@PathVariable("cartid") Long cartid){ 
		System.out.println("jkkkk");
		return cartService.findById(cartid);
	}
	
	@PostMapping
	public Cart post(@RequestBody Cart auth) { 
		auth.setUserid( crc.getCodeCRC32C(rquest.getRemoteUser()));
		return cartService.create(auth);
	}
	
	@PutMapping
	public Cart updateCart(@RequestBody Cart cart){ 
		System.out.println("update");
		return cartService.update(cart);
	}
	
	@DeleteMapping("/{cartid}")
	public void delete(@PathVariable("cartid") Long cartid) { 
		System.out.println("cartid "+ cartid);
		cartService.delete(cartid);
	}
}

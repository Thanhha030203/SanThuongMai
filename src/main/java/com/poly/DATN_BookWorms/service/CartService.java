package com.poly.DATN_BookWorms.service;

import java.util.List;
import java.util.Optional;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Shoponlines;
public interface CartService {
	
	public List<Cart> findAll();
	
	public List<Cart> findByUser();
	
	public List<Shoponlines> list_cart_shop();
	
	public Cart findById(Long cartid);
	
	public Cart create(Cart cart);
	
	public void delete(Long cartid);
	
	public Cart update(Cart cart);

	
	
}

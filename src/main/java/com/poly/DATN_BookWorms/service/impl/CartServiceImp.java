package com.poly.DATN_BookWorms.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.CartRepo;
import com.poly.DATN_BookWorms.service.CartService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.SessionService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CartServiceImp implements CartService {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	CartRepo cartRepo;

    @Autowired
    SessionService sessionService;
    
    @Autowired
    CRC32_SHA256 crc;
    
    @Autowired
    HttpServletRequest request;

	@Override
	public List<Cart> findAll() {
		// TODO Auto-generated method stub
		return cartRepo.findAll();
	}
	
	

	@Override
	public Cart findById(Long cartid) {
		logger.info("find Cart by id with cart id : {}", cartid);
		return cartRepo.findById(cartid).get();
	}

	
	@Override
	public Cart create(Cart book) {
//		 
	
//		Account account = new Account();
//		account = sessionService.get("user");
//		Cart cart = new Cart(account, books);
//		System.out.println("in tại đât");
		// TODO Auto-generated method stub
		return cartRepo.save(book);
	}

	@Override
	public void delete(Long cartid) {
		// TODO Auto-generated method stub
		try {
			logger.info("delete cart with cartid : {}", cartid);
			cartRepo.deleteById(cartid);
			logger.info("delete cart success with cartid : {}", cartid);
		} catch (Exception e) {
			logger.error("Delete cart by id is failed");
			// TODO: handle exception
		}
	}

	@Override
	public Cart update(Cart cart) {
		// TODO Auto-generated method stub
		logger.info("Update cart id with cart for update : {}", cart);
		return cartRepo.save(cart);
	}

	@Override
	public List<Cart> findByUser() {
		System.out.println("userid "+ crc.getCodeCRC32C(request.getRemoteUser()));
		logger.info("find cart by user with userid : ", crc.getCodeCRC32C(request.getRemoteUser()));
		// TODO Auto-generated method stub
		return cartRepo.findCartByUser(crc.getCodeCRC32C(request.getRemoteUser()));
	}

	@Override
	public List<Shoponlines> list_cart_shop() {
		// TODO Auto-generated method stub
		return cartRepo.list_cart_shopId(crc.getCodeCRC32C(request.getRemoteUser()));
	}
	
	
	
}

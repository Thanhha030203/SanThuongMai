package com.poly.DATN_BookWorms.service;

import java.util.List;

import com.poly.DATN_BookWorms.entities.Discountcodes;


public interface DiscountCodeService {

	public List<Discountcodes> findAll();
	
	public Discountcodes findSalesId(String salesid, String userid);
	
	public List<Discountcodes> findDisountForSys(String userid);
	
	public List<Discountcodes> findDisountOfShopWithUser(String intend, String userid, int shopid);
	
	//public List<Shoponlines> list_cart_shop();
	
	public Discountcodes findById(Integer discountid);
	
	public Discountcodes create(Discountcodes discount);
	
	public void delete(Integer discount);
	
	public Discountcodes update(Discountcodes discountid);
	
	public List<Discountcodes> findDisountByUserId(String userid);
	
	
	

}

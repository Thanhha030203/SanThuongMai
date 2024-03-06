package com.poly.DATN_BookWorms.service;

	import java.math.BigDecimal;
	import java.util.Date;
	import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Sales;


public interface SaleService {

	List<Sales> findAll();

	Sales findById(String id);








	Sales create(Sales sales);

	Sales update(Sales sale);

	void delete(String id);

	List<Sales> saleOfShopIntendFor(String intendFor);
	
	List<Sales> saleByShopAndByIntendFor(int shopId,String intendFor);
	




	List<Sales> findAllByShopid(String intendfor, Integer shopid);
}

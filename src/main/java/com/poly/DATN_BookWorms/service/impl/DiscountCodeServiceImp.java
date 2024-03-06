package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Discountcodes;
import com.poly.DATN_BookWorms.repo.DiscountcodesRepo;
import com.poly.DATN_BookWorms.service.DiscountCodeService;


@Service
public class DiscountCodeServiceImp implements DiscountCodeService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	DiscountcodesRepo discountcodesRepo;

	@Override
	public List<Discountcodes> findAll() {
		// TODO Auto-generated method stub
		
		return discountcodesRepo.findAll();
	}

	@Override
	public Discountcodes findSalesId(String salesid, String userid) {
		// TODO Auto-generated method stub
		logger.info("find Discountcodes by saleId : {} and userId : {}",salesid, userid);
		return  discountcodesRepo.findSalesId(salesid, userid);
	}

	@Override
	public Discountcodes findById(Integer discountid) {
		// TODO Auto-generated method stub
		return discountcodesRepo.findById(discountid).get();
	}

	@Override
	public Discountcodes create(Discountcodes discount) {
		// TODO Auto-generated method stub
		return discountcodesRepo.save(discount);
	}

	@Override
	public void delete(Integer discount) {
		// TODO Auto-generated method stub
		discountcodesRepo.deleteById(discount);
	}

	@Override
	public Discountcodes update(Discountcodes discountid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Discountcodes> findDisountForSys(String userid) {
		// TODO Auto-generated method stub
		logger.info("find Discountcodes for hệ thống  by userId : {}", userid);
		return discountcodesRepo.findDisountForSys(userid);
	}

	@Override
	public List<Discountcodes> findDisountOfShopWithUser(String intendfor, String userid, int shopid) {
		// TODO Auto-generated method stub
		return discountcodesRepo.findDisountOfShopWithUser(intendfor, userid, shopid);
	}

	@Override
	public List<Discountcodes> findDisountByUserId(String userid) {
		// TODO Auto-generated method stub
		return discountcodesRepo.findDisountByUserId(userid);
	}



}

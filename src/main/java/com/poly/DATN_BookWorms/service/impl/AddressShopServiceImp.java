package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.entities.AddressShop;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.AddressShopRepo;
import com.poly.DATN_BookWorms.service.AddressShopService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressShopServiceImp implements AddressShopService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	AddressShopRepo addressShopRepo;

	@Override
	public AddressShop findByShop(Shoponlines shopDetail) {
		logger.info("find AddressShop with shoponline :{}", shopDetail);
		return addressShopRepo.findByShop(shopDetail);
	}

	@Override
	public void save(AddressShop addressShop) {
		logger.info("save AddressShop with addressShop :{}", addressShop);
		try {
			addressShopRepo.save(addressShop);
			logger.info("save AddressShop is successfully");
		} catch (Exception e) {
			logger.error("save AddressShop is failed with error : {}",e);
		}
	}

	@Override
	public List<AddressShop> findByShopid(Integer shopId) {
		logger.info("find list AddressShop with shopid :{}", shopId);
		return addressShopRepo.findByShopId(shopId);
	}

	@Override
	public AddressShop findById(Integer addressShopId) {
		logger.info("find AddressShop with addressShopId :{}", addressShopId);
		return addressShopRepo.findById(addressShopId).get();
	}
}

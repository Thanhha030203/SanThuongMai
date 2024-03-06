package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;
import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.service.ShopService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImp implements ShopService {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	ShoponlinesRepo shoponlinesRepo;

	@Autowired
	AccountService accountService;

	@Override
	public Shoponlines findUserId(String userId) {
		logger.info("find shoponelines by userid :{}", userId);
		return shoponlinesRepo.findByUserId(userId);
	}

	@Override
	public void save(Shoponlines shoponlines) {
		logger.info("save shoponelines by shoponlines :{}", shoponlines.toString());
		shoponlinesRepo.save(shoponlines);
	}

	@Override
	public void createShopDefaultWithUser(Account user) {
		logger.info("create shop dèalut with user :{}", user.toString());
		Shoponlines shopDefault = new Shoponlines();
		logger.info("shopdeafault :{}", shopDefault.toString());
		try {
			shopDefault.setUserid(user.getUserid());
			shopDefault.setShopname("Shop Of " + user.getUsername());
			shopDefault.setDescription("IBook is always a reliable choice for book lovers");
			shopDefault.setIsactive(true);
			shoponlinesRepo.save(shopDefault);

			//add shop to accont
			List<Shoponlines> listShopOnline  = new ArrayList<>();
			listShopOnline.add(shopDefault);
			user.setListOfShoponlines(listShopOnline);
			accountService.save(user);
			logger.info("create shopdeafault  is sucesssfully with shopDefault:{}", shopDefault.toString());
		} catch (Exception e) {
			logger.error("create shopdeafault  is failed with shopDefault : {} and exception : {}",
					shopDefault.toString(), e);
		}

	}

	@Override
	public Shoponlines findById(Integer shopId) {
		logger.info("find shoplines  with shopid : {}", shopId);
		return shoponlinesRepo.findById(shopId).get();
	}

}

package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.BooksRepo;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;
import com.poly.DATN_BookWorms.service.ShopOnlinesService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopOnlinesServiceImp implements ShopOnlinesService {
	
	private static final Logger logger = LogManager.getLogger();
	
    @Autowired
    ShoponlinesRepo shoponlinesRepo;

    @Override
    public List<Shoponlines> getAllListShop() {
        return shoponlinesRepo.findAll();
    }

    @Override
    public Shoponlines findById(Integer id) {
        // Sử dụng repository để tìm một ShopOnline theo ID
    	logger.info("findById with shopid : {}",id);
        return shoponlinesRepo.findById(id).orElse(null);
    }
    @Override
    public Shoponlines findShoponlinesByUserId(String userId) {
        return shoponlinesRepo.findByUserId(userId);
    }
}

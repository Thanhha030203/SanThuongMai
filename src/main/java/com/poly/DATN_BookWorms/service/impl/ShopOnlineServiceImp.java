package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;
import com.poly.DATN_BookWorms.service.ShopOnlineService;


@Service
public class ShopOnlineServiceImp implements ShopOnlineService{
	
	private static final Logger logger = LogManager.getLogger();
	 
	@Autowired
	ShoponlinesRepo shoponlinesRepo;
	 
	@Override
	public List<Shoponlines> findAll() {
		// TODO Auto-generated method stub
		return shoponlinesRepo.findAll();
	}

	@Override
	public Shoponlines findById(Integer id) {
		// TODO Auto-generated method stub
		return shoponlinesRepo.findById(id).get();
	}

	@Override
	public Shoponlines create(Books book) {
		//logger.info("find cart by user with userid : ", crc.getCodeCRC32C(request.getRemoteUser()));
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shoponlines update(Books book) {
		// TODO Auto-generated method stub
		return null;
	}

}

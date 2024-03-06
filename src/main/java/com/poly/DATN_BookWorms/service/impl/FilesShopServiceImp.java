package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Files;
import com.poly.DATN_BookWorms.repo.FilesRepo;
import com.poly.DATN_BookWorms.service.FileShopService;


@Service
public class FilesShopServiceImp implements FileShopService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	FilesRepo filesRepo;
	@Override
	public List<Files> getFileByShop(Integer shopid) {
		// TODO Auto-generated method stub
		logger.info("find list Files of Shop by shopid : {} ",shopid);
		return filesRepo.getFilesByShopID(shopid) ;
	}
	@Override
	public List<Files> findAll() {
		// TODO Auto-generated method stub
		return filesRepo.findAll();
	}

}

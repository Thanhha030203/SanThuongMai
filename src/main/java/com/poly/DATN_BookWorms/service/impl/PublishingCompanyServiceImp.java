package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Publishingcompanies;
import com.poly.DATN_BookWorms.repo.PublishingcompaniesRepo;
import com.poly.DATN_BookWorms.service.PublishingCompanyService;
import org.springframework.stereotype.Service;

@Service
public class PublishingCompanyServiceImp implements PublishingCompanyService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	PublishingcompaniesRepo pubRepo;
	
	@Override
	public List<Publishingcompanies> findAll() {
		// TODO Auto-generated method stub
		return pubRepo.findAll();
	}

}

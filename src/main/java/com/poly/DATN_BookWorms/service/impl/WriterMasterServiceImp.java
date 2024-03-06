package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Writtingmasters;
import com.poly.DATN_BookWorms.repo.WrittingmastersRepo;
import com.poly.DATN_BookWorms.service.WriterMasterService;
import org.springframework.stereotype.Service;

@Service
public class WriterMasterServiceImp implements WriterMasterService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	WrittingmastersRepo writtingRepo;
	
	@Override
	public List<Writtingmasters> findAll() {
		// TODO Auto-generated method stub
		return writtingRepo.findAll();
	}

}

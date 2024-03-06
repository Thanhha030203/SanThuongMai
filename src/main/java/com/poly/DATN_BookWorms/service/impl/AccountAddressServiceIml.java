package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Addressusers;
import com.poly.DATN_BookWorms.repo.AddressusersRepo;
import com.poly.DATN_BookWorms.service.AccountAddressService;
import com.poly.DATN_BookWorms.service.AccountService;


@Service
public class AccountAddressServiceIml implements AccountAddressService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	AddressusersRepo addressusersRepo;
	
	@Override
	public List<Addressusers> getAdressByUser(String userid) {
		// TODO Auto-generated method stub
		logger.info("get list Addressuser with userid : {}", userid);
		return addressusersRepo.findByUserId(userid);
	}

	@Override
	public Addressusers findById(String id){
		return addressusersRepo.findById(id).get();
	}


	@Override
	public Addressusers save(Addressusers id){
		return addressusersRepo.save(id);
	}

}

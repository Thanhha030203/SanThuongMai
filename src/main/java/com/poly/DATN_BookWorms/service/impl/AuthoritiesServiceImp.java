package com.poly.DATN_BookWorms.service.impl;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Authorities;
import com.poly.DATN_BookWorms.repo.AccountRepo;
import com.poly.DATN_BookWorms.repo.AuthoritiesRepo;
import com.poly.DATN_BookWorms.service.AuthoritiesService;



@Service
public class AuthoritiesServiceImp implements AuthoritiesService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	AccountRepo accountRepo;
	@Autowired
	AuthoritiesRepo authorityRepo;
	
//	@Override
//	public List<Authorities> findAuthoritiesOfAdministrators() {
//		List<Account> accounts = accountRepo.getAdministrators();
//		return authorityRepo.authoritiesOf(accounts);
//	}

	@Override
	public List<Authorities> findAll() {
		return authorityRepo.findAll();
	}

	@Override
	public Authorities create(Authorities authority) {
		 logger.info("create Authorities is Authorities : {}", authority.toString());
		return  authorityRepo.save(authority);
	}

	@Override
	public void delete(String id) {
		logger.info("delete Authorities is id : {}",id);
		try {
			authorityRepo.deleteById(id);
			logger.info("delete Authorities is successfully");
		} catch (Exception e) {
			logger.info("delete Authorities is failed");
			// TODO: handle exception
		}
	}

	@Override
	public List<Authorities> findAuthoritiesOfAdministrators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Authorities> getOneByRole(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Authorities authorities) {
		logger.info("create Authorities is authorities : {}",authorities);
		try {
			authorityRepo.save(authorities);
			logger.info("create Authorities is sucessfully ");
		} catch (Exception e) {
			logger.info("create Authorities is failed ");
			// TODO: handle exception
		}
	}

//	@Override
//	public List<Authorities> getOneByRole(String username) {
//		// TODO Auto-generated method stub
//		return authorityRepo.getOneByRole(username);
//	}

}

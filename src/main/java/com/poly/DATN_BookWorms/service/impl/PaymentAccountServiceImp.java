package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Paymentaccounts;
import com.poly.DATN_BookWorms.repo.PaymentaccountsRepo;
import com.poly.DATN_BookWorms.service.PaymentAccountService;


@Service
public class PaymentAccountServiceImp implements PaymentAccountService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired 
	PaymentaccountsRepo paymentaccountsRepo;
	
	@Override
	public List<Paymentaccounts> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Paymentaccounts> findWithUser(String userid) {
		// TODO Auto-generated method stub
		logger.info("find list PaymentAccounts by userid : {}",userid);
		return paymentaccountsRepo.findWithUser(userid);
	}

	@Override
	public Paymentaccounts findById(String paycount) {
		return paymentaccountsRepo.findById(paycount).get();
	}

	@Override
	public void save(Paymentaccounts paymentaccount) {
		paymentaccountsRepo.save(paymentaccount);
	}

}

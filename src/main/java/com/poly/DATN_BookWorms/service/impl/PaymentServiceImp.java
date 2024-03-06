package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Payments;
import com.poly.DATN_BookWorms.repo.PaymentsRepo;
import com.poly.DATN_BookWorms.service.PaymentService;


@Service
public class PaymentServiceImp implements PaymentService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	PaymentsRepo paymentsRepo;
	
	@Override
	public List<Payments> saveAll(List<Payments> payments) {
		// TODO Auto-generated method stub
		logger.info("save all payments by payments : {}",payments.size());
		return paymentsRepo.saveAll(payments);
	}

}

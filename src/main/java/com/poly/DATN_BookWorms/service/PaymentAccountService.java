package com.poly.DATN_BookWorms.service;

import java.util.List;

import com.poly.DATN_BookWorms.entities.Paymentaccounts;

public interface PaymentAccountService {
	
	public List<Paymentaccounts> findAll();
	public List<Paymentaccounts> findWithUser(String userid);


    Paymentaccounts findById(String paycount);

    void save(Paymentaccounts paymentaccount);
}

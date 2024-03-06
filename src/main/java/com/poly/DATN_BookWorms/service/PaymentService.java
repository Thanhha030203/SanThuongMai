package com.poly.DATN_BookWorms.service;

import java.util.List;

import com.poly.DATN_BookWorms.entities.Payments;

public interface PaymentService {
	
	List<Payments> saveAll(List<Payments> payments);

}

package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Shippingunits;
import com.poly.DATN_BookWorms.repo.ShippingunitsRepo;
import com.poly.DATN_BookWorms.service.ShippingUnitService;


@Service
public class ShippingUnitServiceImp implements ShippingUnitService {
	
	@Autowired
	ShippingunitsRepo shippingunitsRepo;
	
	@Override
	public List<Shippingunits> getAll() {
		// TODO Auto-generated method stub
		return shippingunitsRepo.findAll();
	}

}

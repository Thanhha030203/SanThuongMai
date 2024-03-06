package com.poly.DATN_BookWorms.service;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	

@Service
public interface ShopOnlinesService {

	
	
	List<Shoponlines> getAllListShop();
	
    Shoponlines findById(Integer cid);
    Shoponlines findShoponlinesByUserId(String userId);
}

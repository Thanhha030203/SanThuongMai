package com.poly.DATN_BookWorms.rest.controller;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.entities.Evaluates;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.DetailBookingService;
import com.poly.DATN_BookWorms.service.EvaluateService;
import com.poly.DATN_BookWorms.service.ShopOnlinesService;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/evaluates")
public class EvaluateRestController {
    @Autowired
    EvaluateService evaluateService;
    @Autowired
    SessionService sessionService;
    @Autowired
    ShopOnlinesService shopOnlinesService;
    @Autowired
    DetailBookingService detailBookingService;
    @GetMapping
    public List<Evaluates> findAll() {
        Account account = sessionService.get("user");
        Shoponlines shoponlines = shopOnlinesService.findShoponlinesByUserId(account.getUserid());
        System.out.println(shoponlines.getShopid());
        return evaluateService.findEvaluatesByShopId(shoponlines.getShopid());
    }
    
    @PostMapping("/save")
	public Evaluates postEvaluates(@RequestBody Evaluates evaluateData) { 
    	System.out.println("success 2222222");
    	System.out.println("1234"+ evaluateData.getDbid());
    	Detailbookings d = detailBookingService.dtb(evaluateData.getDbid());
    	if(d.bookings.orderstatusid != 8) {
    		System.out.println("tt" + d.bookings.getOrderstatusid());
    		d.bookings.setOrderstatusid(8);
    	}
		try {
			
			return evaluateService.create(evaluateData);
		} catch (Exception e) {
			System.out.println("errr"+ e);
			return null;
			// TODO: handle exception
		}
	}

}

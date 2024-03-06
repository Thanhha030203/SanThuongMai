package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Bookings;
import com.poly.DATN_BookWorms.entities.Discountcodes;
import com.poly.DATN_BookWorms.service.DiscountCodeService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/discount")
public class DiscountCodeRestController {

	@Autowired
	DiscountCodeService discountCodeService;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	CRC32_SHA256 crc32_SHA256;
	
	@PostMapping("")
	public Discountcodes getDiscountCodeBySaleid(@RequestBody Discountcodes discountcodes) { 
		String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
		System.out.println("in d : "+ discountcodes.toString());
		System.out.println("in : "+   discountCodeService.findSalesId(discountcodes.saleid, userid));
		Discountcodes dis = discountCodeService.findSalesId(discountcodes.saleid, userid);
		if(dis != null) { 
			return dis;
		}
		else { 
			discountcodes.userid = userid;
			return discountCodeService.create(discountcodes);
		
		}
		
	}
	
	
	@GetMapping("/{saleid}")
	public Discountcodes createDisscount(@PathVariable("saleid") String saleid) { 
		String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
		System.out.println("in : "+   discountCodeService.findSalesId(saleid, userid));
		return discountCodeService.findSalesId(saleid, userid);
	}
	
	

	@GetMapping("/findbyUser")
	public List<Discountcodes> findbyUser() { 
		
		return discountCodeService.findDisountByUserId(crc32_SHA256.getCodeCRC32C(request.getRemoteUser()));
	}
	
	@GetMapping
	public List<Discountcodes> findAll(){ 
		return  discountCodeService.findAll();
	}
	
	@DeleteMapping("/{dis}")
	public void delete(@PathVariable("dis") Integer dis) { 
		 discountCodeService.delete(dis);
	}
	
	@DeleteMapping("/dele/{sa}")
	public void deleteSa(@PathVariable("sa") String sa) {
		String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
		Discountcodes dis = discountCodeService.findSalesId(sa, userid);
		 discountCodeService.delete(dis.discountcodeid);
	}
	
	@GetMapping("/view/{discountcodeid}")
	public Discountcodes getBookingId(@PathVariable Integer discountcodeid ) {
		System.out.println("discountcodeid: " + discountcodeid);
//		Discountcodes d = discountCodeService.findById(discountcodeid);
//		System.out.println(d.sales.promotionname);
		return discountCodeService.findById(discountcodeid);
	}
}

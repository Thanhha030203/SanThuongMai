package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.ShopOnlineService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/shoponline")
public class ShopOnlineRestController {

	@Autowired
	ShopOnlineService shopOnlineService;
	
	@GetMapping("/{id}")
	public Shoponlines getOne(@PathVariable("id") Integer id) { 
		return shopOnlineService.findById(id);
	}
	
	@GetMapping("")
	public List<Shoponlines> getAll() { 
		return shopOnlineService.findAll();
	}
}

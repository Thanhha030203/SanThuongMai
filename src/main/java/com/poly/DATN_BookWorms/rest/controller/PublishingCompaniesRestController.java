package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Publishingcompanies;
import com.poly.DATN_BookWorms.service.PublishingCompanyService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/publishcompany")
public class PublishingCompaniesRestController {

	@Autowired
	PublishingCompanyService publishingCompanyService;
	
	@GetMapping
	public List<Publishingcompanies> getAllPC(){ 
		return publishingCompanyService.findAll();
	}
}

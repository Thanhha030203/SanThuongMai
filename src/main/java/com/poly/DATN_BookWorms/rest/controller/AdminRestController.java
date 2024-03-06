package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.repo.BooksRepo;
import com.poly.DATN_BookWorms.service.BookService;
import com.poly.DATN_BookWorms.service.DetailBookingService;
@CrossOrigin("*")
@RestController

@RequestMapping("/rest/admin")
public class AdminRestController {

	@Autowired
	DetailBookingService detailBookingService;
	
//	@GetMapping("/bestSeller")
//	public ResponseEntity<List<Books>> bestSeller() {
//		List<Books> listBooks= detailBookingService.findTop5Seller();
//		System.out.println(listBooks.toString());
//		return ResponseEntity.ok(listBooks);
//
//	}
//
//	@GetMapping("/inventory")
//	public ResponseEntity<List<Books>> inventory() {
//		List<Books> listBooks = detailBookingService.findTop5Inventory();
//		return ResponseEntity.ok(listBooks);
//	}

}

package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Imagebooks;
import com.poly.DATN_BookWorms.service.impl.ImagesBookServiceImp;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/imagebook")
public class ImagesBookRestController {
	
	@Autowired
	ImagesBookServiceImp imagesBookServiceImp;
	
	@GetMapping("/{bookid}")
	public List<Imagebooks> getImageBookByBookid(@PathVariable("bookid") Long bookid){ 
		return  imagesBookServiceImp.findByBookId(bookid);
	}
	
}

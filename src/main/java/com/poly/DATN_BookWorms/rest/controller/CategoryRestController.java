package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import com.poly.DATN_BookWorms.service.TypeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.poly.DATN_BookWorms.entities.Categories;
import com.poly.DATN_BookWorms.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryService categoryService;

	@Autowired
	TypeBookService typeBookService;
	
	@GetMapping()
	public List<Categories> getAll() {
		return categoryService.findAll();
	}
	@PostMapping("/cateWithBook")
	public List<Categories> getCateWithBookId(@RequestParam("bookid") String bookId) {
		return typeBookService.findCateByBookId(Integer.parseInt(bookId));
	}
	
}

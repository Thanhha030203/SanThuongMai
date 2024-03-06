package com.poly.DATN_BookWorms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.service.BookService;


@Controller
@RequestMapping("/book")
public class BookController {

	// @Autowired
	// BookService bookService;
	
	// @RequestMapping("/detail")
	// public String bookDetail(@RequestParam("bookId") Integer bookId, Model model) { 
	// 	Books book = bookService.findById(bookId);
	// 	model.addAttribute("bookDetail", book);
	// 	return "Client/Product_page/detail_product";
	// }
	
	// @RequestMapping("/list")
	// public String bookList(Model model) { 
	// 	List<Books> bookList= bookService.findAll();
	// 	model.addAttribute("booklist",bookList);
	// 	return "Client/Product_page/product_list";
	// }
	
}

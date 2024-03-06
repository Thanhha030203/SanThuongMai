package com.poly.DATN_BookWorms.controller;

import com.poly.DATN_BookWorms.entities.*;
import com.poly.DATN_BookWorms.service.BookService;
import com.poly.DATN_BookWorms.service.CategoryService;
import com.poly.DATN_BookWorms.service.EvaluatesService;
import com.poly.DATN_BookWorms.service.PublishingCompanyService;
import com.poly.DATN_BookWorms.service.ShopOnlinesService;
import com.poly.DATN_BookWorms.service.TypeBookService;
import com.poly.DATN_BookWorms.service.WriterMasterService;
import com.poly.DATN_BookWorms.service.WriterService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	TypeBookService typeBookService;

	@Autowired
	WriterService writerService;

	@Autowired
	BookService bookService;
	
	@Autowired
	EvaluatesService evaluatesService;

	
	@Autowired
	CategoryService categoryService;
	@Autowired
	PublishingCompanyService publishingCompanyService;
	@Autowired
	WriterMasterService writerMasterService;

	@Autowired
	CRC32_SHA256 crc;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse resp;

	@Autowired
	ShopOnlinesService shopOnlinesService;


	@GetMapping("/list")
	public String listBooks(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "asc") String priceSort,
			@RequestParam("categories") Optional<Integer> cate,@RequestParam("newYear") Optional<String> newyear
			,@RequestParam("sold") Optional<String> sold) {

		Sort priceDirection;
		if ("desc".equalsIgnoreCase(priceSort)) {
			priceDirection = Sort.by(Sort.Direction.DESC, "price");
		} else {
			priceDirection = Sort.by(Sort.Direction.ASC, "price");
		}
		if(newyear.isPresent()) {
			priceDirection = Sort.by(Sort.Direction.DESC, "publishingyear");
		}
		if(newyear.isPresent()) {
			priceDirection = Sort.by(Sort.Direction.DESC, "quantitysold");
		}
		Pageable pageable = PageRequest.of(page, size, priceDirection);
		List<Categories> categories = categoryService.findAll();
		List<Publishingcompanies> publishingcompanies = publishingCompanyService.findAll();
		List<Writtingmasters> writtingmasters = writerMasterService.findAll();

		Page<Books> bookPage;
		if (cate.isPresent()) {
			System.out.println("in ra cate : " + cate.get());
			bookPage = bookService.getBooksByCategoryID(cate.get(), pageable);
		} else {
			bookPage = bookService.findAll(pageable);
		}
		model.addAttribute("currentPage", page);
		model.addAttribute("categories", categories);
		model.addAttribute("publishingcompanies", publishingcompanies);
		model.addAttribute("writtingmasters", writtingmasters);
		model.addAttribute("books", bookPage.getContent());
		model.addAttribute("totalPages", bookPage.getTotalPages());
		return "Client/Product_page/product_list";
	}


	@GetMapping("/detail/{bookid}")
	public String detail(@PathVariable("bookid") Long id, Model model) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		// System.out.println("lkjlskjlajf");
		Books item = bookService.findById(id);
		item.setProductviews(item.getProductviews()+1);
		bookService.update(item);
		System.out.println("lkjlskjlajssf" + id);
		List<Books> b = bookService.getBooksByCategoryID(item.getListOfTypebooks().get(0).categories.categoryid);
		List<Evaluates> eva_list = evaluatesService.getEvaByBookid(id);
//		List<String> images = imagebookService.findByBookId(id);
//		System.out.print(images);
//		model.addAttribute("images", images);
Shoponlines list1 = shopOnlinesService.findById(item.shopid);
Integer total = evaluatesService.sumDbidByEvaluateId(item.shopid);
model.addAttribute("total", total);
model.addAttribute("profile", list1);
System.out.println("139 : "+ list1.listOfBooks.size());
		model.addAttribute("item", item);
		model.addAttribute("eva", eva_list);
		model.addAttribute("books", b);
		model.addAttribute("userid", crc.getCodeCRC32C(request.getRemoteUser()));
		
    //    model.addAttribute("total", total);
		// System.out.println("ll"+ item.getListOfImagebooks().get(0).getName());
		return "Client/Product_page/detail_product";
	}


//	public Pageable support(Sort priceDirection, int page, int size, Model model) {
//		Sort sort = priceDirection;
//
//
//		return pageable;
//	}
}

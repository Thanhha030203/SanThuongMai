package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Typebooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Categories;
import com.poly.DATN_BookWorms.repo.TypebooksRepo;
import com.poly.DATN_BookWorms.service.TypeBookService;


@Service
public class TypeBookServiceImp implements TypeBookService {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	TypebooksRepo typebooksRepo;

	@Override
	public List<Categories> getCategoriesWithShop(Integer shopid) {
		 logger.info("get list categories with shopid :{}", shopid);
		// TODO Auto-generated method stub
		return typebooksRepo.listCategoriesByType(shopid);
	}

	@Override
	public Typebooks create(Books books, Categories categories) {
		Typebooks typebooks = new Typebooks();
		typebooks.setBookid(books.getBookid().intValue());
		typebooks.setCategories(categories);
		typebooks.setBooks(books);
		return typebooksRepo.save(typebooks);
	}

	@Override
	public Typebooks save(Typebooks typebooks) {
		return typebooksRepo.save(typebooks);
	}

	@Override
	public List<Categories> findCateByBookId(int bookid) {
		return typebooksRepo.findCateByBookId(bookid);
	}
}
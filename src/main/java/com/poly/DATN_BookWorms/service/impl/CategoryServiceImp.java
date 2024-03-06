package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import com.poly.DATN_BookWorms.entities.Typebooks;
import com.poly.DATN_BookWorms.service.TypeBookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Categories;
import com.poly.DATN_BookWorms.repo.CategoriesRepo;
import com.poly.DATN_BookWorms.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	CategoriesRepo cateRepo;
	
	@Override
	public List<Categories> findAll() {
		// TODO Auto-generated method stub
		return cateRepo.findAll();
	}

}

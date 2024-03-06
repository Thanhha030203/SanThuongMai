package com.poly.DATN_BookWorms.service;

import java.util.List;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Shoponlines;

public interface ShopOnlineService {
	List<Shoponlines> findAll();

	Shoponlines findById(Integer id);


	Shoponlines create(Books book);

	Shoponlines update(Books book);
}

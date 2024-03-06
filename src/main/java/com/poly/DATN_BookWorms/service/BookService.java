package com.poly.DATN_BookWorms.service;

import java.util.List;
import com.poly.DATN_BookWorms.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Publishingcompanies;
import org.springframework.web.multipart.MultipartFile;

import com.poly.DATN_BookWorms.entities.*;
import com.poly.DATN_BookWorms.response.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public interface BookService {

	List<Books> findAll();

	Books findById(Long id);
	Page<Books> findAll(Pageable pageable);
	
	List<Publishingcompanies> getPCWithShop(Integer shopid);





	Books creates(Books books);

	Books update(Books book);

	void delete(Long id);
	Page<BookResponse> findAllBook(Pageable pageable);

	List<Books> getBooksByCategoryID(Integer categoryID);
	Books findTopBookByQuantitySold();
	Page<Books> getBooksByCategoryID(Integer categories, Pageable pageable);
	Page<Books> findByshopid(Integer shopid, Pageable pageable);
	List<Books> findByshopidv2(Integer shopid);
	List<Books> findTop5LowestQuantityBooksByShopId(Integer shopId);


	Page<Books> findBooksNew(Pageable pageable);

	void updateIsActive(Long bookId, boolean newIsActive);
	List<Books> findByShopList(Integer shopid);

	List<Integer> getBookWithTypeBook(List<Integer> listtype);
	List<Integer> getBookWithWriters(List<Integer> listwriter);
	List<Integer> getBookWithEvaluate(List<Integer> listeva);



	void deleteBook(Long bookId, boolean newIsActive);

	 Books save(Books book);

	Books getNewBook();
}

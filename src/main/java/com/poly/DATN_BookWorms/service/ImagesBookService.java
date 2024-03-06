package com.poly.DATN_BookWorms.service;

import java.util.List;

import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Imagebooks;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesBookService {
	
	public List<Imagebooks> findByBookId(Long bookid);


    List<Imagebooks> create(MultipartFile[] images, Books books);
}

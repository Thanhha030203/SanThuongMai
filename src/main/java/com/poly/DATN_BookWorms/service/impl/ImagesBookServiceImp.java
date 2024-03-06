package com.poly.DATN_BookWorms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Imagebooks;
import com.poly.DATN_BookWorms.repo.ImagebooksRepo;
import com.poly.DATN_BookWorms.service.ImagesBookService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagesBookServiceImp implements ImagesBookService {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ImagebooksRepo imagebooksRepo;
	
	@Override
	public List<Imagebooks> findByBookId(Long bookid) {
		// TODO Auto-generated method stub
		System.out.println("In ra "+ imagebooksRepo.findByBookid(bookid));
		logger.info("find list imagesBook with bookid : {}", bookid);
		return imagebooksRepo.findByBookid(bookid);
	}
	@Override
	public List<Imagebooks> create(MultipartFile[] images, Books books) {
		List<Imagebooks> savedImagebooks = new ArrayList<>();

		for (MultipartFile image : images) {
			try {
				String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
				String uploadDir = "D:/Work/DATN_BookWorms/src/main/resources/static/Client/images";
				Imagebooks imagebooks = new Imagebooks();
				FileUploadUtil.saveFile(uploadDir, fileName, image);
				imagebooks.setBookid(books.getBookid().intValue());
				imagebooks.setName(fileName);
				imagebooks.setTypefile("image");
				imagebooksRepo.save(imagebooks);
				savedImagebooks.add(imagebooks);
			} catch (IOException e) {
				System.out.println("not save image");
				e.printStackTrace();
			}
		}

return savedImagebooks;
	}

}

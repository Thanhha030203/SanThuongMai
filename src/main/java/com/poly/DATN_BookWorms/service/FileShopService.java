package com.poly.DATN_BookWorms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.DATN_BookWorms.entities.Files;
import com.poly.DATN_BookWorms.repo.FilesRepo;

public interface FileShopService {
	
	public List<Files> getFileByShop(Integer shopid);
	
	public List<Files> findAll();
	
	
}

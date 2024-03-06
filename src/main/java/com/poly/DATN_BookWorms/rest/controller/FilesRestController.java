package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Files;
import com.poly.DATN_BookWorms.service.FileShopService;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/files")
public class FilesRestController {

	@Autowired
	FileShopService fileShopService;

	@GetMapping("/{id}")
	public List<Files> getFileByShop(@PathVariable("id") Integer shopid) {
		return fileShopService.getFileByShop(shopid);
	}

}

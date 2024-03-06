package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Files;

public interface FilesRepo extends JpaRepository<Files, Integer>{

	@Query("Select f from Files f where f.shopid like ?1")
	public List<Files> getFilesByShopID(Integer shopid);
}

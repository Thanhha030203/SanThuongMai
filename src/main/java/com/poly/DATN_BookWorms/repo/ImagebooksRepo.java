package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Imagebooks;

public interface ImagebooksRepo extends JpaRepository<Imagebooks, Integer>{

	@Query("Select i from Imagebooks i where i.books.bookid like ?1")
	public List<Imagebooks> findByBookid (Long bookid);
}

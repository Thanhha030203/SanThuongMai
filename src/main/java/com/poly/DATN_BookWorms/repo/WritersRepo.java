package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Writers;
import com.poly.DATN_BookWorms.entities.Writtingmasters;

public interface WritersRepo extends JpaRepository<Writers, Integer>{
	@Query("Select w.writtingmasters from Writers w where w.bookid in (Select b.bookid from Books b where b.shopid = ?1)")
    List<Writtingmasters> listWrittingByType(Integer shopid);
}

package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Categories;
import com.poly.DATN_BookWorms.entities.Typebooks;

public interface TypebooksRepo extends JpaRepository<Typebooks, Integer> {

    @Query("Select t.categories from Typebooks t where t.bookid in (Select b.bookid from Books b where b.shopid = ?1)")
    List<Categories> listCategoriesByType(Integer shopid);
    @Query("Select t.categories from Typebooks t where t.bookid  = ?1")
    List<Categories> findCateByBookId(int bookid);
}

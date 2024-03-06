package com.poly.DATN_BookWorms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.DATN_BookWorms.entities.Categories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriesRepo extends JpaRepository<Categories, Integer>{

    @Query("SELECT o FROM Categories o WHERE o.categoryid in :categoryIds")
    List<Categories> findByInId(@Param("categoryIds") List<Integer> categoryIds);
}

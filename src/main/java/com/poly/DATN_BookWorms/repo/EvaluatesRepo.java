package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.DATN_BookWorms.entities.Evaluates;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface EvaluatesRepo extends JpaRepository<Evaluates, Integer>{
    @Query("SELECT COUNT(e.dbid) FROM Evaluates e WHERE e.evaluateid = :id")
    Integer sumDbidByEvaluateId(Integer id);
    @Query("Select e from Evaluates e where e.dbid in (Select d.dbid from Detailbookings d where d.books.bookid = ?1)")
    List<Evaluates> getEvaByBookid(Long bookid);
    
    
    
}

package com.poly.DATN_BookWorms.service;

import com.poly.DATN_BookWorms.entities.Evaluates;
import com.poly.DATN_BookWorms.repo.EvaluatesRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface EvaluatesService {

    Integer sumDbidByEvaluateId(Integer id);
    
     List<Evaluates> getEvaByBookid(Long bookid);
}

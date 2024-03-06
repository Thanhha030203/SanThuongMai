package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.entities.Evaluates;
import com.poly.DATN_BookWorms.repo.EvaluatesRepo;
import com.poly.DATN_BookWorms.service.EvaluatesService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluatesServiceImp implements EvaluatesService {
	
	private static final Logger logger = LogManager.getLogger();
	
    @Autowired
    EvaluatesRepo evaluatesRepo;
    @Override
    public Integer sumDbidByEvaluateId(Integer id) {
    	logger.info("find sumDbidByEvaluateId by id : {}",id);
        return evaluatesRepo.sumDbidByEvaluateId(id);
    }	
    
    @Override
	public List<Evaluates> getEvaByBookid(Long bookid) {
    	logger.info("find list Evalautes by bookid : {}",bookid);
		// TODO Auto-generated method stub
	return evaluatesRepo.getEvaByBookid(bookid);
	}
}

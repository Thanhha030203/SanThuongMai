package com.poly.DATN_BookWorms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Writtingmasters;


public interface WriterMasterService {

	public List<Writtingmasters> findAll();

}

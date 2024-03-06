package com.poly.DATN_BookWorms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.DATN_BookWorms.entities.Evaluates;

@Service
public interface EvaluateService {

	Evaluates create(Evaluates evaluate);



    List<Evaluates> findEvaluatesByShopId(Integer shopId);

    public List<Evaluates> findAll();

}

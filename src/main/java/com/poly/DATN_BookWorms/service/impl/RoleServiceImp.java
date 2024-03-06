package com.poly.DATN_BookWorms.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.entities.Roles;
import com.poly.DATN_BookWorms.repo.RoleRepo;
import com.poly.DATN_BookWorms.service.RoleService;

@Service
public class RoleServiceImp implements RoleService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	RoleRepo roleRepo;
	
	@Override
	public List<Roles> findAll() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}

	@Override
	public Roles findByName(String name){
		return new Roles();
	}

	@Override
	public Roles findSellerByRoleId(String roleId) {
		logger.info("find seller role by roleid :{}", roleId);
		return roleRepo.findById(roleId).get();
	}

	@Override
	public Roles save(Roles roles) {
		logger.info("save role by roles input :{}", roles.toString());
		return roleRepo.save(roles);
	}
}

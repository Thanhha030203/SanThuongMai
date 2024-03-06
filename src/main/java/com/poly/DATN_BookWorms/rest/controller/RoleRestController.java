package com.poly.DATN_BookWorms.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.DATN_BookWorms.entities.Roles;
import com.poly.DATN_BookWorms.service.RoleService;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/roles")
public class RoleRestController {

	@Autowired
	RoleService roleService;
	
	@GetMapping
	public List<Roles> getAll(){
		return roleService.findAll();
	}
	
}

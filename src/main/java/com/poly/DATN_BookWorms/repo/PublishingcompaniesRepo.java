package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Publishingcompanies;
import com.poly.DATN_BookWorms.entities.Writtingmasters;

public interface PublishingcompaniesRepo extends JpaRepository<Publishingcompanies, Integer>{
}

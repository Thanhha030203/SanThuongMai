package com.poly.DATN_BookWorms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.DATN_BookWorms.entities.Payments;

public interface PaymentsRepo extends JpaRepository<Payments, String>{

}

package com.poly.DATN_BookWorms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Paymentaccounts;

public interface PaymentaccountsRepo extends JpaRepository<Paymentaccounts, String>{
	@Query("Select p from Paymentaccounts p where p.account.userid = ?1")
	public List<Paymentaccounts> findWithUser(String userid);	
}

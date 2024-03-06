package com.poly.DATN_BookWorms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.DATN_BookWorms.entities.Hassales;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HassalesRepo extends JpaRepository<Hassales, Integer>{
    @Query("SELECT h FROM Hassales h WHERE h.saleid = :couponCode")
    List<Hassales> findAllByCouponCode(@Param("couponCode") String couponCode);
    
    List<Hassales> findAllBysaleid(String saleId);

}

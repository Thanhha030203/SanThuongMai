package com.poly.DATN_BookWorms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.DATN_BookWorms.entities.Shoponlines;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShoponlinesRepo extends JpaRepository<Shoponlines, Integer>{

    @Query(value = "SELECT o FROM Shoponlines o where o.account.userid = ?1")
    Shoponlines findByUserId(String userId);
}

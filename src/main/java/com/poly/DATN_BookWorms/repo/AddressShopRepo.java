package com.poly.DATN_BookWorms.repo;

import com.poly.DATN_BookWorms.entities.AddressShop;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressShopRepo extends JpaRepository<AddressShop, Integer> {
    @Query("SELECT o FROM AddressShop o WHERE o.shoponlines = ?1")
    public AddressShop findByShop(Shoponlines shopDetail);
    @Query("SELECT o FROM AddressShop o WHERE o.shoponlines.shopid = ?1")
    List<AddressShop> findByShopId(Integer shopId);
}

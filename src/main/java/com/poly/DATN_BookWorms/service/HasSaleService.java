package com.poly.DATN_BookWorms.service;

import com.poly.DATN_BookWorms.entities.Hassales;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HasSaleService {
    List<Hassales> findAllByCouponCode(String couponCode);

    Hassales saveHassales(Hassales hassales);

    List<Hassales> findAllBysaleid(String saleId);
}

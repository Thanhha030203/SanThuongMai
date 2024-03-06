package com.poly.DATN_BookWorms.service;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {


    public Shoponlines findUserId(String userId);

    void save(Shoponlines shoponlines);

    void createShopDefaultWithUser(Account user);

    Shoponlines findById(Integer shopId);

}

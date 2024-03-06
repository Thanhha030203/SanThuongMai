package com.poly.DATN_BookWorms.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.poly.DATN_BookWorms.entities.*;
import com.poly.DATN_BookWorms.repo.DiscountcodesRepo;
import com.poly.DATN_BookWorms.repo.HassalesRepo;
import com.poly.DATN_BookWorms.service.ShopOnlinesService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DATN_BookWorms.repo.SalesRepo;
import com.poly.DATN_BookWorms.service.SaleService;

@Service
public class SaleServiceImp implements SaleService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	SalesRepo saleRepo;
	@Autowired
	HassalesRepo hassalesRepo;
	@Autowired
	DiscountcodesRepo discountcodesRepo;
	@Autowired
	SessionService session;
	@Autowired
	CRC32_SHA256 crc32Sha256;
	@Autowired
	ShopOnlinesService shopOnlinesService;
	@Override
	public List<Sales> findAll() {
		// TODO Auto-generated method stub
		return saleRepo.findAll();
	}

	@Override
	public Sales findById(String id) {
		logger.info("find sale with saleid : {}",id);
		return saleRepo.findById(id).get();
	}

	@Override
	public Sales create(Sales sales) {
		Account account = session.get("user");
		LocalDate currentDate = LocalDate.now();
	Shoponlines shoponlines = shopOnlinesService.findShoponlinesByUserId(account.getUserid());
		String authorityId = crc32Sha256.getCodeCRC32C(sales.getPromotionname() + sales.getStatuses());
		sales.setCouoponcode(authorityId);
		sales.setPromotionname(sales.getPromotionname());
		sales.setCreateat(new Date());
		sales.setDescriptions(sales.getDescriptions());
		sales.setDiscountpercentage(sales.getDiscountpercentage());
		sales.setStatuses(sales.getStatuses());
		sales.setIntendfor(sales.getIntendfor());
		sales.setShopid(shoponlines.getShopid());
		return saleRepo.save(sales);
	}

	@Override
	public Sales update(Sales sale) {
		logger.info("update sale with sale : {}",sale);
		return saleRepo.save(sale);
	}

	@Override
	public void delete(String id) {
		logger.info("delete sales with id : {}",id);
		saleRepo.deleteById(id);;
	}

	@Override
	public List<Sales> saleOfShopIntendFor(String intendFor){
		logger.info("get list sales with intendFor : {}",intendFor);
		return saleRepo.sales_of_shop_for_intendfor(intendFor);
	}

	@Override
	public List<Sales> saleByShopAndByIntendFor(int shopId, String intendFor) {
		// TODO Auto-generated method stub
		return  saleRepo.getSaleByShopAndByIntendfor(shopId, intendFor);
	}


    public List<Sales> findAllByShopid(String intendfor, Integer shopid) {
		List<Sales> allSales = saleRepo.findAllByshopid(shopid);
        List<Sales> filteredSales = allSales.stream()
                .filter(sale -> sale.getIntendfor().equals(intendfor))
                .collect(Collectors.toList());

        return filteredSales;
    }

}

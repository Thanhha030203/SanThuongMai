package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.entities.PaymentShop;
import com.poly.DATN_BookWorms.entities.Paymentaccounts;
import com.poly.DATN_BookWorms.repo.PaymentShopRepo;
import com.poly.DATN_BookWorms.service.PaymentShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaymentShopServiceImpl implements PaymentShopService {

    @Autowired
    PaymentShopRepo paymentShopRepo;
    @Override
    public List<PaymentShop> findByShopId(Integer shopId) {

        return   paymentShopRepo.findByShopId(shopId);
    }

    @Override
    public Float getMonthPaid(Integer shopId,Date startDate, Date endDate,boolean status) {
        if ( paymentShopRepo.getMonthPaid( shopId, startDate,endDate,status)!=null){
            return  paymentShopRepo.getMonthPaid( shopId, startDate,endDate,status);
        }
        return Float.valueOf(0);
    }

    @Override
    public Float getTotal(Integer shopId,boolean statusPayment) {

        if (paymentShopRepo.getTotal(shopId, statusPayment)!=null){
            return paymentShopRepo.getTotal(shopId, statusPayment);
        }
        return Float.valueOf(0);
    }

    @Override
    public void save(PaymentShop paymentShop) {
         paymentShopRepo.save(paymentShop);
    }

	@Override
	public List<PaymentShop> findAll() {
		// TODO Auto-generated method stub
		return paymentShopRepo.findAll();
	}
}

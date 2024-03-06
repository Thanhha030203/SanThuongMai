package com.poly.DATN_BookWorms.service.impl;

import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.repo.DetailbookingsRepo;
import com.poly.DATN_BookWorms.service.DetailBookingService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailBookingServiceImp implements DetailBookingService {

	private static final Logger logger = LogManager.getLogger();
	
    @Autowired
    DetailbookingsRepo detailbookingsRepo;
    @Override
    public List<Detailbookings> findByAll() {

        return detailbookingsRepo.findAll();
    }

    @Override
    public Detailbookings dtb(String id) {
    	logger.info("get DetalBooking with id : {}",id);
        return detailbookingsRepo.findById(id).get();
    }

    @Override
    public List<Detailbookings> findByBookingId(String bookingId) {
    	logger.info("get DetalBooking with bookingId : {}",bookingId);
        return detailbookingsRepo.findByBookingId(bookingId);
    }
}

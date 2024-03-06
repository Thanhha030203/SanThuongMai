package com.poly.DATN_BookWorms.service;

import com.poly.DATN_BookWorms.entities.Detailbookings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DetailBookingService {
    List<Detailbookings> findByAll();

    Detailbookings  dtb(String id);

    List<Detailbookings> findByBookingId(String bookingId);

}

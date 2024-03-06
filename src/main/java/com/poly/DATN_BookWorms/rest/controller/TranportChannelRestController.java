package com.poly.DATN_BookWorms.rest.controller;


import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Bookings;

import com.poly.DATN_BookWorms.service.BookingService;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/rest/tranportChannel")
public class TranportChannelRestController {
    @Autowired
    BookingService bookingService;
    @Autowired
    SessionService service;

    @GetMapping("/{orderstatusid}")
    public List<Bookings> findByOrderstatusid(@PathVariable Integer orderstatusid) {
        Account account = service.get("user");
        return bookingService.findBookingsByShopIdAndOrderStatusID(account.getListOfShoponlines().get(0).getShopid(), orderstatusid);
    }
    @GetMapping("/all")
    public List<Bookings> findAll() {
        Account account = service.get("user");
        return bookingService.findBookingsByShopId(account.getListOfShoponlines().get(0).getShopid());
    }

    @PutMapping("/{bookingId}/{status}/updateOrderStatus")
    public void updateOrderStatus(@PathVariable String bookingId, @PathVariable Integer status) {
        bookingService.updateOrderStatus(bookingId, status);
    }


}

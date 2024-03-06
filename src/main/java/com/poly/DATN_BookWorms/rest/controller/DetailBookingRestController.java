package com.poly.DATN_BookWorms.rest.controller;

import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.service.DetailBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rest/detailBooking")
public class DetailBookingRestController {
    @Autowired
    DetailBookingService detailBookingService;


    @GetMapping
    public List<Detailbookings> detailbooking(){

        return detailBookingService.findByAll();
    }

    @GetMapping("/{id}")
    public Detailbookings detailbooking(@PathVariable("id")  String a){
        return detailBookingService.dtb(a);
    }

}

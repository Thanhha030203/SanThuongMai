package com.poly.DATN_BookWorms.response;

import lombok.Data;

@Data
public class DetailBookingResponse {
    String bookname;
    Integer quantity;

    public DetailBookingResponse(String bookname, int quantity) {
        this.bookname = bookname;
        this.quantity = quantity;
    }
}


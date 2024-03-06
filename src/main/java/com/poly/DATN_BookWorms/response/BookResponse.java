package com.poly.DATN_BookWorms.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    Integer bookid;
    String bookname;
    String image;
    Double price;
    String shopname;
}

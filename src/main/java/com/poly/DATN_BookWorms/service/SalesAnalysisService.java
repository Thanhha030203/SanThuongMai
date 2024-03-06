package com.poly.DATN_BookWorms.service;

import com.poly.DATN_BookWorms.beans.BookRankingToNumber;
import com.poly.DATN_BookWorms.beans.BookRankingToSales;
import com.poly.DATN_BookWorms.beans.CategoryRanking;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Categories;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface SalesAnalysisService {
    double getMonthSales(Date startDate, Date endDate);

    int getMonthOrder(Date startDate, Date endDate);

    int getProductView(Integer shopId);

    List<CategoryRanking> getCategoryRanking();

    List<BookRankingToSales> getBookRankingToSales();

    List<BookRankingToNumber> getBookRankingToNumber();

    List<Books> getBookRankingToView();
}

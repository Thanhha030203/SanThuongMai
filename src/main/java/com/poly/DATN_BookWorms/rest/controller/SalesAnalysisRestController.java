package com.poly.DATN_BookWorms.rest.controller;

import com.poly.DATN_BookWorms.beans.BookRankingToNumber;
import com.poly.DATN_BookWorms.beans.BookRankingToSales;
import com.poly.DATN_BookWorms.beans.CategoryRanking;
import com.poly.DATN_BookWorms.beans.Sales;
import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Categories;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.SalesAnalysisService;
import com.poly.DATN_BookWorms.service.ShopService;
import com.poly.DATN_BookWorms.utils.ConvertStringToDate;
import com.poly.DATN_BookWorms.utils.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/salesAnalysis")
public class SalesAnalysisRestController {

    @Autowired
    SalesAnalysisService salesAnalysisService;
    @Autowired
    ConvertStringToDate convertStringToDate;
    @Autowired
    SessionService sessionService;
    @Autowired
    ShopService shopService;

    @PostMapping(value = "/getSalesAnalysis", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Sales>> getSalesAnalysis(@RequestParam("year") String year) {
        List<Sales> listSales = new ArrayList<>();
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
//        // lấy thông số thống kê
        for (int i = 1; i <= 12; i++) {
            YearMonth yearMonth = YearMonth.of(Integer.parseInt(year), i);

            // Lấy ngày đầu tháng
            LocalDate ngayDauThang = yearMonth.atDay(1);

            // Lấy ngày cuối cùng của tháng
            LocalDate ngayCuoiThang = yearMonth.atEndOfMonth();

            // Chuyển đổi LocalDate sang Date
            Date startDate = Date.from(ngayDauThang.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());

            // Date dateNgayCuoiThang
            Date endDate = Date.from(ngayCuoiThang.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
            //lấy doanh số theo tháng năm
            Sales sales = new Sales();
            sales.setMonthlySales(salesAnalysisService.getMonthSales(startDate, endDate));
            sales.setOrders(salesAnalysisService.getMonthOrder(startDate, endDate));
            if (sales.getOrders() != 0) {
                sales.setSalesPerOrder(sales.getMonthlySales() / sales.getOrders());
            } else {
                sales.setSalesPerOrder(0);
            }
            sales.setPagesViews(salesAnalysisService.getProductView(shoponlines.getShopid()));
            if (sales.getPagesViews() != 0) {
                sales.setConversionRate((sales.getMonthlySales() / sales.getPagesViews()));
            } else {
                sales.setConversionRate(0);
            }
            listSales.add(sales);
        }

        return ResponseEntity.ok(listSales);
    }

    @GetMapping("/categoryRanking")
    public ResponseEntity<List<CategoryRanking>> getCategoryRanking() {

        List<CategoryRanking> listCategoryRanking = salesAnalysisService.getCategoryRanking();
        System.out.println("Category"+listCategoryRanking.toString());
        return ResponseEntity.ok(listCategoryRanking);
    }

    @GetMapping("/accordingToSales")
    public ResponseEntity<List<BookRankingToSales>> getAccordingToSales() {
        List<BookRankingToSales> listBookRankingToSales = salesAnalysisService.getBookRankingToSales();
        System.out.println("Book Ranking To Sales"+listBookRankingToSales.toString());
        return ResponseEntity.ok(listBookRankingToSales);
    }
    @GetMapping("/accordingToView")
    public ResponseEntity<List<Books>> getAccordingToView() {
        List<Books> listBookRankingToView = salesAnalysisService.getBookRankingToView();
        System.out.println("Book Ranking To View"+listBookRankingToView.toString());
        return ResponseEntity.ok(listBookRankingToView);
    }
    @GetMapping("/productNumber")
    public ResponseEntity<List<BookRankingToNumber>> getProductNumber() {
        List<BookRankingToNumber> listBookRankingToNumber = salesAnalysisService.getBookRankingToNumber();
        System.out.println("Book Ranking To Number"+listBookRankingToNumber.toString());
        return ResponseEntity.ok(listBookRankingToNumber);
    }
}

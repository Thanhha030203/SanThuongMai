package com.poly.DATN_BookWorms.rest.controller;

import com.poly.DATN_BookWorms.beans.AnalysisFinance;
import com.poly.DATN_BookWorms.beans.CategoryRanking;
import com.poly.DATN_BookWorms.beans.Sales;
import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.PaymentShop;
import com.poly.DATN_BookWorms.entities.Paymentaccounts;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.PaymentAccountService;
import com.poly.DATN_BookWorms.service.PaymentShopService;
import com.poly.DATN_BookWorms.service.ShopService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/revenueFinance")
public class RevenueFinanceRestController {

    @Autowired
    SessionService sessionService;

    @Autowired
    ShopService shopService;

    @Autowired
    PaymentShopService paymentShopService;

    @Autowired
    PaymentAccountService paymentAccountService;
    @Autowired
    CRC32_SHA256 crc32Sha256;
    @GetMapping("/getRevenue")
    public ResponseEntity<Shoponlines> getRevenue() {
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
        return ResponseEntity.ok(shoponlines);
    }
    @GetMapping("/getListFinance")
    public ResponseEntity<List<PaymentShop>> getListFinance() {
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
        List<PaymentShop> listFinance = paymentShopService.findByShopId(shoponlines.getShopid());
        return ResponseEntity.ok(listFinance);
    }
    @GetMapping("/getAnalysisFinance")
    public ResponseEntity<AnalysisFinance> getAnalysisFinance() {
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
        //get starts and endtime Month now
        YearMonth yearMonth = YearMonth.now();
        int monthNumber = yearMonth.getMonthValue();

        // Lấy ngày đầu tháng
        LocalDate ngayDauThang = yearMonth.atDay(1);

        // Lấy ngày cuối cùng của tháng
        LocalDate ngayCuoiThang = yearMonth.atEndOfMonth();

        // Chuyển đổi LocalDate sang Date
        Date startDate = Date.from(ngayDauThang.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());

        // Date dateNgayCuoiThang
        Date endDate = Date.from(ngayCuoiThang.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
        //
        float monthPaid = paymentShopService.getMonthPaid(shoponlines.getShopid(),startDate,endDate,true);
        float totalPaid = paymentShopService.getTotal(shoponlines.getShopid(),true);
        float totalUnPaid = paymentShopService.getTotal(shoponlines.getShopid(),false);
        AnalysisFinance analysisFinance = new AnalysisFinance(monthPaid,totalPaid,totalUnPaid);
        return ResponseEntity.ok(analysisFinance);
    }
    @PostMapping(value = "/sendRequestPayment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaymentShop> createUser(@RequestParam("paymentTotal") String paymentTotal) {
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
        // Lưu đối tượng người dùng vào cơ sở dữ liệu
        PaymentShop  paymentShop = new PaymentShop();
        paymentShop.setCreateat(new Date());
        paymentShop.setStatus(false);
        paymentShop.setValuepayment(Long.parseLong(paymentTotal));
        paymentShop.setShoponlines(shoponlines);
        paymentShop.setIsdelete(false);

        paymentShopService.save(paymentShop);
        // Trả về phản hồi thành công
        return ResponseEntity.ok(paymentShop);
    }
    @GetMapping("/getAccountBalance")
    public ResponseEntity<Paymentaccounts> getAccountBalance() {
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
        Paymentaccounts accountBalance = new Paymentaccounts();
        if (shoponlines.getPaycount() != null){
           accountBalance = paymentAccountService.findById(shoponlines.getPaycount());
        }
        return ResponseEntity.ok(accountBalance);
    }

    @PostMapping(value = "/saveAccountBalance" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Paymentaccounts> saveAccountBalance(@RequestBody   @Valid Paymentaccounts paymentaccount) {
        Account user = sessionService.get("user");
        Shoponlines shoponlines = shopService.findUserId(user.getUserid());
        String paymentAccountId = crc32Sha256.getCodeCRC32C(paymentaccount.getAccountnumber()+paymentaccount.getCccd()) ;

        paymentaccount.setPaid(paymentAccountId);
        paymentAccountService.save(paymentaccount);
        shoponlines.setPaycount(paymentAccountId);

        shopService.save(shoponlines);
        return ResponseEntity.ok(paymentaccount);
    }
}

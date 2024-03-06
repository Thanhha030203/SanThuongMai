package com.poly.DATN_BookWorms.rest.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.entities.Payments;
import com.poly.DATN_BookWorms.service.*;

import com.poly.DATN_BookWorms.service.BookingService;
import com.poly.DATN_BookWorms.service.CartService;
import com.poly.DATN_BookWorms.service.MailService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.MailBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Addressusers;
import com.poly.DATN_BookWorms.entities.Bookings;
import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.service.BookingService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/bookings")
public class BookingRestController {
    Logger logger = LogManager.getLogger(Example.class);
    @Autowired
    BookingService bookingService;

    @Autowired
    AccountService accountService;

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    DetailBookingService detailBookingService;
    @Autowired
    CRC32_SHA256 crc;

    @Autowired
    MailBody mailBody;

    @Autowired
    MailService mailService;

    @PostMapping()
    public Bookings create(@RequestBody JsonNode bookingData, HttpServletRequest request) {
        return bookingService.create(bookingData);
    }

    @GetMapping("")
    public List<Bookings> getAll() {
        return bookingService.findAll();
    }
    @GetMapping("/user")
    public List<Bookings> booking() {

        return bookingService.findByUserId(crc.getCodeCRC32C(httpServletRequest.getRemoteUser()));
    }

    @GetMapping("/findByBookingId/{bookingId}")
    public List<Detailbookings> findByBookingId(@PathVariable String bookingId) {
        return detailBookingService.findByBookingId(bookingId);
    }

    @GetMapping("/findByBookingIdDetail/{bookingId}")
    public Bookings findByBookingIdDetail(@PathVariable String bookingId) {
        return bookingService.findByBookingId(bookingId);
    }

    @GetMapping("/generate/{bookingid}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable String bookingid) {
        try {
            Rectangle pageSize = new Rectangle(PageSize.A6);

            float marginLeft = 0.5f;
            float marginRight = 0.5f;
            float marginTop = 0.5f;
            float marginBottom = 0.5f;

            Document document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            BaseFont unicodeFont = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(unicodeFont, 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
            com.itextpdf.text.Font contentFont = new com.itextpdf.text.Font(unicodeFont, 12, BaseColor.BLACK.getRGB());
            Bookings bookings = bookingService.findByBookingId(bookingid);
            List<Detailbookings> detailbookings = detailBookingService.findByBookingId(bookingid);

            // Create a centered Paragraph for the title
            Paragraph titleParagraph = new Paragraph(detailbookings.get(0).getBooks().getShoponlines().getShopname().toUpperCase() + " STORE", titleFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);

            document.add(new Paragraph("    ------------------------------------------------------------------    ", contentFont));
            String inputString = "    ------------------------------------------------------------------    ";
            Addressusers adduser = bookings.listOfPayments.get(0).getAddressusers();
            System.out.println("Số lượng dấu gạch ngang: " + inputString.length());
            document.add(new Paragraph("    Ngày: " + new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss").format(new Date()), contentFont));
            document.add(new Paragraph("    Mã hóa đơn: " + bookings.getBookingid(), contentFont));
            document.add(new Paragraph("    ------------------------------------------------------------------    ", contentFont));
            document.add(new Paragraph("    Người mua: "+ adduser.fullname, contentFont));
            document.add(new Paragraph("    Địa chỉ: "+adduser.getDetailhome()+", "+adduser.getWard()+", "+adduser.getDistrict()+", "+adduser.getProvince(), contentFont));
            document.add(new Paragraph("    Số điện thoại: "+ adduser.getPhonenumber(), contentFont));
            document.add(new Paragraph("    ------------------------------------------------------------------    ", contentFont));

            // Center-align the product details table
            document.add(new Paragraph("            Số lượng                                    Giá", contentFont));
            document.add(new Paragraph("    ------------------------------------------------------------------    ", contentFont));
            for (Detailbookings detailbooking : detailbookings) {
                Paragraph productDetails = new Paragraph("    " + detailbooking.getBooks().getBookname(), contentFont);
                productDetails.add(new Paragraph("                " + detailbooking.getQuantity() + "                                         " + detailbooking.getBooks().getPrice()));
                productDetails.setAlignment(Element.ALIGN_LEFT);
                document.add(productDetails);
            }


            // Center-align the total cost and thank you message
            document.add(new Paragraph("    ------------------------------------------------------------------    ", contentFont));
            Paragraph totalCostParagraph = new Paragraph("Tổng cộng:                         " + bookings.getCost(), contentFont);
            totalCostParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(totalCostParagraph);
            document.add(new Paragraph("    ------------------------------------------------------------------    ", contentFont));
            Paragraph thankYouParagraph = new Paragraph("Cảm ơn quý khách đã mua hàng!", contentFont);
            thankYouParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(thankYouParagraph);

            document.close();
            byte[] pdfContents = baos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.setContentDispositionFormData("inline", "example.pdf");
            System.out.println("jkldsjfdllllllllll");
            return new ResponseEntity<>(pdfContents, headers, HttpStatus.OK);
        } catch (Exception e) {
            
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	@GetMapping("/{bookingId}")
	public List<Detailbookings> getBookingId(@PathVariable String bookingId ) {
		Bookings b = bookingService.findById(bookingId).get();
	List<Detailbookings> d = b.getListOfDetailbookings();
		return d;
	}
	
	@PostMapping("/update")
	public Bookings updateStatusBooking(@RequestBody String json) throws MessagingException{
		logger.info("huỷ starty...");
		System.out.println("huỷ hoá đơn2");
		 Bookings b = bookingService.findById(json).get();
		b.setOrderstatusid(6);
		try {
			logger.info("huỷ starty...1");
			System.out.println("huỷ hoá đơn1" + json.toString());
			Account account = accountService.findByUserId(crc.getCodeCRC32C(httpServletRequest.getRemoteUser()));
			  String subject ="THÔNG BÁO XÁC NHẬN HUỶ ĐƠN HÀNG";
			  String personCancle = "";
			  if(b.userid.equals(account.userid)) { 
				  personCancle = "Người mua";
			  }
			  else { 
				  personCancle = "Người bán";
			  }
			  String buyer = mailBody.mailHuyDon(b.account.getFullname(), personCancle, b, "Huỷ Thành Công");
			  String shoper = mailBody.mailHuyDon(b.getListOfDetailbookings().get(0).books.shoponlines.getShopname(),personCancle,b, "Huỷ Thành Công");
			  bookingService.update(b);
			mailService.send(b.account.getEmail(),subject, buyer);
			mailService.send(b.getListOfDetailbookings().get(0).books.shoponlines.account.getEmail(),subject, shoper);
			System.out.println("huỷ hoá đơn");
		} catch (Exception e) {
			System.out.println("lỗi không huỷ đơn đc : "+ e);
			logger.info("huỷ end...1");
			// TODO: handle exception
		}
		return b;
	}

}

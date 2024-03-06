package com.poly.DATN_BookWorms.rest.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.poly.DATN_BookWorms.beans.MailInformation;
import com.poly.DATN_BookWorms.config.PaymentConfig;
import com.poly.DATN_BookWorms.entities.Bookings;
import com.poly.DATN_BookWorms.entities.PaymentShop;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.PaymentShopRepo;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;
import com.poly.DATN_BookWorms.service.impl.MailServiceImp;
import com.poly.DATN_BookWorms.service.BookingService;
import com.poly.DATN_BookWorms.utils.SessionService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/payment")
public class AdminPaymentRestController {

	@Autowired
	PaymentShopRepo paymentShopRepo;
	@Autowired
	ShoponlinesRepo shopOnlinesRepo;
	@Autowired
	MailServiceImp mailer;

	@Autowired
	BookingService bookingService;

	@Autowired
	SessionService session;

	@GetMapping("/payment-callback")
	public void paymentCallback(@RequestParam Map<String, String> queryParams, HttpServletResponse response)
			throws IOException, NumberFormatException, NotFoundException, MessagingException {
		MailInformation mailInfo = new MailInformation();
		String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
		String paymentShopId = queryParams.get("paymentshopid");
		if (paymentShopId != null && !paymentShopId.equals("")) {
			if ("00".equals(vnp_ResponseCode)) {
				// Giao dịch thành công
				// Thực hiện các xử lý cần thiết, ví dụ: cập nhật CSDL
				PaymentShop paymentShop = paymentShopRepo.findById(Integer.parseInt(queryParams.get("paymentshopid")))
						.orElseThrow(() -> new NotFoundException());
				paymentShop.setStatus(true);
				Shoponlines shopOnlines = paymentShopRepo.findShopId(paymentShop.getShoponlines().getShopid());

				System.out.println("hi " + paymentShop.getShoponlines().getTotal());
				String vnp_TxnRef = queryParams.get("vnp_TxnRef");
	            String vnp_Amount = queryParams.get("vnp_Amount");
	            String vnp_OrderInfo = queryParams.get("vnp_OrderInfo");
				System.out.println("hi1 " + vnp_TxnRef);
				System.out.println("hi2 " + vnp_Amount);
				System.out.println("hi3 " + vnp_OrderInfo);

				shopOnlines.setTotal(shopOnlines.getTotal() - paymentShop.getValuepayment());
				shopOnlinesRepo.save(shopOnlines);
				paymentShopRepo.save(paymentShop);
				mailInfo.setTo(paymentShop.getShoponlines().getAccount().getEmail());
				mailInfo.setSubject("IBook chào bạn! Yêu cầu thanh toán của bạn đã được xử lý");
				String body = "VNPay_TxnRef: " + vnp_TxnRef + " VNPay_Amount: " + vnp_Amount + " VNPay_OrderInfo: " + vnp_OrderInfo;
				mailInfo.setBody(body);
				mailer.send(mailInfo);
				response.sendRedirect("http://localhost:8080/api/payment/callpayment");
				} else {
				// Giao dịch thất bại
				// Thực hiện các xử lý cần thiết, ví dụ: không cập nhật CSDL\
				response.sendRedirect("http://localhost:4200/payment-failed");

			}
		}

	}

	@GetMapping("/payment-callback2")
	public void paymentCallback2(@RequestParam Map<String, String> queryParams, HttpServletResponse response)
	throws IOException, NumberFormatException, NotFoundException, MessagingException {
				System.out.println("ọidkfjklsfjl");
	
	response.sendRedirect("http://localhost:8080/order/success");
	}

	@GetMapping("/create_payment/{paymentshopid}&{valuepayment}")
	public RedirectView createPayment(@PathVariable("paymentshopid") long paymentshopid,
			@PathVariable("valuepayment") long valuepayment, HttpServletRequest request)
			throws UnsupportedEncodingException {

		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";

		long amount = valuepayment * 100;
		String bankCode = "NCB";

		String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
		String vnp_IpAddr = "127.0.0.1";

		String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		vnp_Params.put("vnp_BankCode", bankCode);
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);

		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl + "?paymentshopid=" + paymentshopid);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(paymentUrl);

		return redirectView;
	}

	@GetMapping("/create_payment2")
	public RedirectView createPaymentUser( @RequestParam("totalAllFinalprice") String str,HttpServletRequest request, HttpServletResponse response)
	throws UnsupportedEncodingException {
		long price = Long.parseLong(str.replaceAll(",", "").replace(".00", ""));
		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";
		long amount = price * 100;
		String bankCode = "NCB";

		String vnp_TxnRef = PaymentConfig.getRandomNumber(8);
		String vnp_IpAddr = "127.0.0.1";

		String vnp_TmnCode = PaymentConfig.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		vnp_Params.put("vnp_BankCode", bankCode);
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);

		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", PaymentConfig.vnp_ReturnUrl2 );
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
		System.out.println("122121222222222222222aSsff22222222222222222222222");
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		System.out.println("122222aaaa22222222222aSsff22222222222222222222222");
		String queryUrl = query.toString();
		String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = PaymentConfig.vnp_PayUrl + "?" + queryUrl;
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(paymentUrl);
		System.out.println("1111112222222222222222aSsff22222222222222222222222"+ redirectView);
		return redirectView;
	}
	
	
	public void fin(JsonNode json){ 
		session.set("b", json);
	}

	public Bookings create(JsonNode json){ 
		 return bookingService.create(json);
	}
	
}



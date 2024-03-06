package com.poly.DATN_BookWorms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.DATN_BookWorms.entities.Addressusers;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Discountcodes;
import com.poly.DATN_BookWorms.entities.Paymentaccounts;
import com.poly.DATN_BookWorms.entities.Sales;
import com.poly.DATN_BookWorms.entities.Shippingunits;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.AccountAddressService;
import com.poly.DATN_BookWorms.service.CartService;
import com.poly.DATN_BookWorms.service.DiscountCodeService;
import com.poly.DATN_BookWorms.service.PaymentAccountService;
import com.poly.DATN_BookWorms.service.SaleService;
import com.poly.DATN_BookWorms.service.ShippingUnitService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	CartService cartService;

	@Autowired
	SaleService saleService;

	@Autowired
	CRC32_SHA256 crc32_SHA256;

	@Autowired
	HttpServletRequest request;

	@Autowired
	AccountAddressService accountAddressService;

	@Autowired
	DiscountCodeService discountCodeService;

	@Autowired
	ShippingUnitService shippingUnitService;
	
	@Autowired
	PaymentAccountService paymentAccountService;

	@GetMapping
	public String orderPage(Model model) {
		try {
			String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
			List<Addressusers> addressusser = accountAddressService.getAdressByUser(userid);
			model.addAttribute("addressusser", addressusser);
			List<Discountcodes> discountcode = discountCodeService.findDisountForSys(userid);
			model.addAttribute("discountcode", discountcode);
			List<Shippingunits> shippingunit = shippingUnitService.getAll();
			model.addAttribute("shippingunit", shippingunit);
			List<Paymentaccounts> paymentaccount = paymentAccountService.findWithUser(userid);
			model.addAttribute("paymentaccount", paymentaccount);
			logger.info("get order Page");
		} catch (Exception e) {
			logger.info("Error during controller with error :{}",e);
		}
		return "Client/cart_client/deal";
	}
	
	@PostMapping("/payment")
	public String OrderPayment(Model model) {
		return "redirect:/cart";
	}

	@GetMapping("/success")
	public String success(Model model){ 
		return "Client/cart_client/success";
	}
}

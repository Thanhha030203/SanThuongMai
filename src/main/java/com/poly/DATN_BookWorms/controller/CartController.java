package com.poly.DATN_BookWorms.controller;

import java.util.List;
import java.util.Optional;

import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.SessionService;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Discountcodes;
import com.poly.DATN_BookWorms.entities.Sales;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.service.CartService;
import com.poly.DATN_BookWorms.service.DiscountCodeService;
import com.poly.DATN_BookWorms.service.SaleService;

@Controller
@RequestMapping("/cart")
public class CartController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	CartService cartService;

	@Autowired
	SaleService saleService;
	
	@Autowired
	DiscountCodeService discountCodeService;
	
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	CRC32_SHA256 crc32_SHA256;
	

	@RequestMapping
	public String cartView(Model model, HttpServletRequest request) {
		try {
			List<Cart> cartuser_list = cartService.findByUser();
			model.addAttribute("cartuserList", cartuser_list);
			List<Shoponlines> list_cart_shop = cartService.list_cart_shop();
			model.addAttribute("cartshoplist", list_cart_shop);
			
			String username = request.getRemoteUser();
			model.addAttribute("requestusername", username);
			  List<Discountcodes> findDisountForSys = discountCodeService.findDisountForSys(crc32_SHA256.getCodeCRC32C(req.getRemoteUser()));
	           System.out.println("in dis1 "+ findDisountForSys);
			model.addAttribute("saleShopIntendFor", findDisountForSys);
			logger.info("get Cart page");
		} catch (Exception e) {
			logger.info("Error during cart controller with error :{}", e);
		}
		return "Client/cart_client/cart_user";
	}

	@RequestMapping("/delete/{cartid}")
	public String deletecart(@PathVariable("cartid") Long cartid) {
		cartService.delete(cartid);
		return "redirect:/cart";
	}

//	@RequestMapping("/shopOnline")
//	public String cartLinkShop(@RequestParam("shopId") Integer shopId) { 
//		
//		return "Client/Product_page/product_shop_list";
//	}

//	@RequestMapping("/product")
//	public String cartLinkProduct() { 
//		return "Client/Product_page/detail_product";
//	}
//	

//	@RequestMapping("/product")
//	public String d() { 
//		return "Client/Product_page/product_shop_list";
//	}
//		@GetMapping("cart/client")
//	public String e() { 
//		return "Client/cart_client/cart_user";
//	}
//			@GetMapping("purch")
//	public String g() { 
//		return "Client/cart_client/deal";
//	}
}

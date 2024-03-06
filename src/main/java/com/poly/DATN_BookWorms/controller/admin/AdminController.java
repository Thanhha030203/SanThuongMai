package com.poly.DATN_BookWorms.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.DATN_BookWorms.entities.Bookings;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.repo.BooksRepo;
import com.poly.DATN_BookWorms.repo.DetailbookingsRepo;
import com.poly.DATN_BookWorms.response.DetailBookingResponse;
import com.poly.DATN_BookWorms.service.BookingService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	BookingService bookingService;
	@Autowired
	BooksRepo booksRepo;
	
	public void display(Model model) {
		long countUnpaid = bookingService.countUnpaid();
		model.addAttribute("countUnpaid", countUnpaid);

		long countPaid = bookingService.countPaid();
		model.addAttribute("countPaid", countPaid);

		long countConfirm = bookingService.countConfirm();
		model.addAttribute("countConfirm", countConfirm);

		long countDelivering = bookingService.countDelivering();
		model.addAttribute("countDelivering", countDelivering);

		long countProcessed = bookingService.countProcessed();
		model.addAttribute("countProcessed", countProcessed);

		long countCancel = bookingService.countCancel();
		model.addAttribute("countCancel", countCancel);

		long countRefund = bookingService.countRefund();
		model.addAttribute("countRefund", countRefund);
	}
	
	@RequestMapping("/index")
    public String index(Model model){
        return "admin/index";
    }
	
	@GetMapping("/findOrderUser")
	public String findByOrderUser(Model model) {
		List<Bookings> item = bookingService.findAll();
		model.addAttribute("item", item);
		display(model);
		return "admin/findOrderUser";
	}

	@GetMapping("/unpaid")
	public String unpaid(Model model) {
		List<Bookings> item = bookingService.unpaid();
		model.addAttribute("item", item);
		display(model);
		return "admin/unpaid";
	}

	@GetMapping("/paid")
	public String paid(Model model) {
		List<Bookings> item = bookingService.unpaid();
		model.addAttribute("item", item);
		display(model);
		return "admin/paid";
	}

	@GetMapping("/confirm")
	public String confirm(Model model) {
		List<Bookings> item = bookingService.confirm();
		display(model);
		model.addAttribute("item", item);
		return "admin/confirm";
	}

	@GetMapping("/delivering")
	public String delivering(Model model) {
		List<Bookings> item = bookingService.delivering();
		model.addAttribute("item", item);
		display(model);
		return "admin/delivering";
	}

	@GetMapping("/processed")
	public String processed(Model model) {
		List<Bookings> item = bookingService.processed();
		model.addAttribute("item", item);
		display(model);
		return "admin/processed";
	}

	@GetMapping("/cancel")
	public String cancel(Model model) {
		List<Bookings> item = bookingService.cancel();
		model.addAttribute("item", item);
		display(model);
		return "admin/cancel";
	}

	@GetMapping("/refund")
	public String refund(Model model) {
		List<Bookings> item = bookingService.refund();
		model.addAttribute("item", item);
		display(model);
		return "admin/refund";
	}

	@GetMapping("/findtop5")
	public String findTop5ByOrderSoldDesc(Model model) {
		List<Books> item = booksRepo.findTop5Seller();
		model.addAttribute("item", item);

		List<Books> item1 = booksRepo.findTop5Inventory();
		model.addAttribute("item1", item1);
		return "admin/findtop5";
	}
	
	@GetMapping("/createvoucher")
	public String createVoucher(Model model) {
		
		return "admin/createvoucher";
	}
}


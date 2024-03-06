package com.poly.DATN_BookWorms.service.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Bookings;
import com.poly.DATN_BookWorms.entities.Books;
import com.poly.DATN_BookWorms.entities.Detailbookings;
import com.poly.DATN_BookWorms.entities.Payments;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import com.poly.DATN_BookWorms.repo.BookingsRepo;
import com.poly.DATN_BookWorms.repo.BooksRepo;
import com.poly.DATN_BookWorms.repo.DetailbookingsRepo;
import com.poly.DATN_BookWorms.repo.ShoponlinesRepo;
import com.poly.DATN_BookWorms.service.AccountService;
import com.poly.DATN_BookWorms.service.BookingService;
import com.poly.DATN_BookWorms.service.MailService;
import com.poly.DATN_BookWorms.service.PaymentService;
import com.poly.DATN_BookWorms.utils.CRC32_SHA256;
import com.poly.DATN_BookWorms.utils.MailBody;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BookingServiceImp implements BookingService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	BookingsRepo bookingRepo;
	@Autowired
	DetailbookingsRepo detailRepo;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	BooksRepo booksRepo;
	
	@Autowired
	ShoponlinesRepo shoponlinesRepo;
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	CRC32_SHA256 crc32_SHA256;

	@Autowired
	AccountService accountService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	MailBody mailBody;

	@Autowired
	MailService mailService;

	
	String email = "";
	String shopname = "";

	long bookid = 0;
	int countdetal =0;
	@Override
	public Bookings create(JsonNode bookingData) {
		
		logger.info("create bookings start.....");
		logger.info("input JsonNode with bookingData : {}",bookingData.toString());
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			Bookings booking = mapper.convertValue(bookingData, Bookings.class);
			int a = ThreadLocalRandom.current().nextInt(1000,9999);
			
			logger.info("have booking for create : {}",booking.toString());
			String userid = crc32_SHA256.getCodeCRC32C(request.getRemoteUser());
			logger.info("userid for booking : {}",userid);
			booking.setUserid(userid);
			booking.setBookingid(crc32_SHA256.getCodeCRC32C(booking.getUserid()+booking.getCreateat()+ booking.getBookingid()+a));
			booking.getAccount().setUserid(userid);
		
			if(booking.getCostvoucher() == null || (booking.getCostvoucher() <= 0)){
				booking.setCostvoucher(0.00);
			}
			booking.setCostvoucher(booking.getCostvoucher()* booking.getCost());
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH,3); 
			booking.setTimefinish(c.getTime());
			
			bookingRepo.save(booking);
			logger.info("Create booking is successful with booking : {}", booking);
			TypeReference<List<Detailbookings>> type = new TypeReference<List<Detailbookings>>() {};
			TypeReference<List<Payments>> type1 = new TypeReference<List<Payments>>() {};
			List<Detailbookings> details = mapper.convertValue(bookingData.get("listOfDetailbookings"), type);
			logger.info("list detalbooking in booking have size : {}", details.size());
			logger.info("list detalbooking start.... ");
						details.stream().peek(d ->{	
							if(d == null) { 
								
							}
							else { 
								logger.info("Detailbooking : {}", d.toString());
								Books books = booksRepo.findById((long) d.bookid).get();
								
								books.setQuantitysold(books.getQuantitysold() + d.getQuantity());
								books.setQuantity(books.getQuantity() - d.getQuantity());
								if(books.getQuantity() ==0 ) { 
									books.setStatues("Hết hàng");
									books.setIsactive(false);
								}
								d.setBookingid(booking.getBookingid());
								 d.setBookings(booking);
								 d.setDbid(crc32_SHA256.getCodeCRC32C(userid+d.bookingid+d.bookid));
								 bookid = d.bookid;
								 logger.info("Detailbooking for create : {}", d.toString());
								 detailRepo.save(d);
							email = d.getDbid();
							
								// shopname =  d.getBooks().getShoponlines().getShopname();
								 booksRepo.save(books);
								 logger.info("Detailbooking for create is success full : {}", d.toString());
							}
						}).collect(Collectors.toList());

						
			List<Payments> payment = mapper.convertValue(bookingData.get("listOfPayments"), type1);
			logger.info("list Payment in booking have size : {}", payment.size());
			logger.info("list Payment start.... ");
			payment.stream().peek(d -> 
					{
						logger.info("Payment : {}", d.toString());
						d.setBookingid(booking.getBookingid());
						d.setBookings(booking);
						d.setPaymentid(crc32_SHA256.getCodeCRC32C(userid+ d.getBookingid()));
						if(d.getType()) { 
							//System.out.println("tttttttttttttttttttttttttttttttttttttttttttt12 "+ d.getType());
						//	Bookings book = bookingRepo.findBybookingid(d.getBookingid());
						Shoponlines shopon = booksRepo.s(bookid);

							shopon.setTotal(shopon.getTotal() + booking.getCost());
							d.setStatus("Đã thanh toán");
							booking.getOrderstatuses().setOrderstatusid(4);
							booking.setOrderstatusid(4);
							bookingRepo.save(booking);	
						}
						else { 
							d.setPaid(null);
							System.out.println("payment now");
							d.setStatus("Chưa thanh toán");
							booking.getOrderstatuses().setOrderstatusid(3);
							booking.setOrderstatusid(3);
							bookingRepo.save(booking);	
							System.out.println("payment nows");}
						
					}).collect(Collectors.toList());
			logger.info("list Payment in booking have size : {}", payment.size());
			paymentService.saveAll(payment);
			System.out.println("now detailkflds;");
	
			sendMailSuccess(booking,countdetal, email);
			logger.info("Create bookings, detailbookings, payments is successfully");
			return booking;
		} catch (Exception e) {
			logger.error("Create bookings, detailbookings, payments is failed with error : {}", e);
			return null;
			// TODO: handle exception
		}
	}

	 
	public void sendMailSuccess(Bookings bookings,int count , String email) throws MessagingException{ 
		Bookings booking = bookingRepo.findById(bookings.bookingid).get();
		System.out.println("email");
		Detailbookings detailbookings = detailRepo.findById(email).get();
		System.out.println("in detlkdd"+ detailbookings.toString());
		String subject ="THÔNG BÁO ĐẶT ĐƠN THÀNH CÔNG";
		String personCancle = booking.account.fullname;
		
		String buyer = mailBody.mailSuccess(personCancle,personCancle, booking, "Đặt hàng thành công",count);
		String shoper = mailBody.mailSuccess(detailbookings.getBooks().getShoponlines().getShopname(),personCancle,booking, "Đặt hàng thành công",count);
	  mailService.send(booking.account.getEmail(),subject, buyer);
	 mailService.send(detailbookings.getBooks().getShoponlines().getAccount().getEmail(),subject, shoper);
	  System.out.println("thành công ");
	}
	@Override
	public Optional<Bookings> findById(String id) {
		// TODO Auto-generated method stub
		return  bookingRepo.findById(id);
	}
	

	@Override
	public List<Bookings> findByUserId(String userId) {
		logger.info("get booking by userid have userId : {}", userId);
		return bookingRepo.findBookingByUser(userId);
	}

	@Override
	public List<Bookings> findAll() {
		return bookingRepo.findAll();
	}

	@Override

	public List<Bookings> findByStatusId(String orderStatusId) {
		logger.info("get booking by orderStatusId have orderStatusId : {}", orderStatusId);
		return bookingRepo.ListBookings_Status(orderStatusId);
	}


//	@Override
//	public List<Bookings> findAllByUserId(String userId) {
//		return bookingRepo.findByuserid(userId);
//	}
//	@Override
//	public List<Bookings> findByUserIdAndOrderStatusId(String userId, Integer orderStatusId) {
//		List<Bookings> allByUserId = findAllByUserId(userId);
//
//		return allByUserId.stream()
//				.filter(booking -> booking.getOrderstatusid().equals(orderStatusId))
//				.collect(Collectors.toList());
//	}


	@Override
	public Bookings findByBookingId(String bookingId) {
		return bookingRepo.findBybookingid(bookingId);
	}

//	@Override
//	public List<Bookings> findAllByUserId(String userId) {
//		return bookingRepo.findByuserid(userId);
//	}
//	@Override
//	public List<Bookings> findByUserIdAndOrderStatusId(String userId, Integer orderStatusId) {
//		List<Bookings> allByUserId = findAllByUserId(userId);
//
//		return allByUserId.stream()
//				.filter(booking -> booking.getOrderstatusid().equals(orderStatusId))
//				.collect(Collectors.toList());
//	}
	@Override
	public List<Bookings> findBookingsByShopIdAndOrderStatusID(Integer shopId, Integer orderStatusId) {
		List<Bookings> allBookings =bookingRepo.findBookingsByShopId(shopId);

		return allBookings.stream()
				.filter(booking -> booking.getOrderstatusid().equals(orderStatusId))
				.collect(Collectors.toList());
	}

	
	@Override
	public List<Bookings> unpaid() {
		// TODO Auto-generated method stub
		return bookingRepo.unpaid();
	}

	@Override
	public List<Bookings> paid() {
		// TODO Auto-generated method stub
		return bookingRepo.paid();
	}

	@Override
	public List<Bookings> confirm() {
		// TODO Auto-generated method stub
		return bookingRepo.confirm();
	}

	@Override
	public List<Bookings> delivering() {
		// TODO Auto-generated method stub
		return bookingRepo.delivering();
	}

	@Override
	public List<Bookings> processed() {
		// TODO Auto-generated method stub
		return bookingRepo.processed();
	}

	@Override
	public List<Bookings> cancel() {
		// TODO Auto-generated method stub
		return bookingRepo.cancel();
	}

	@Override
	public List<Bookings> refund() {
		// TODO Auto-generated method stub
		return bookingRepo.refund();
	}
	public long countUnpaid() {
        return bookingRepo.countUnpaid();
    }
	
	public long countPaid() {
        return bookingRepo.countPaid();
    }
	
	public long countConfirm() {
        return bookingRepo.countConfirm();
    }
	
	public long countDelivering() {
        return bookingRepo.countDelivering();
    }
	
	public long countProcessed() {
        return bookingRepo.countProcessed();
    }
	
	public long countCancel() {
        return bookingRepo.countCancel();
    }
	
	public long countRefund() {
        return bookingRepo.countRefund();
    }

	@Override
	public Bookings update(Bookings bookingId) {
		// TODO Auto-generated method stub
		return bookingRepo.save(bookingId);
	}

	@Override
	public Bookings byBookingUserId(String bookingUserId) {
		// TODO Auto-generated method stub
		return bookingRepo.findById(bookingUserId).get();	
	}

	@Override
	public List<Bookings> findBookingsByShopId(Integer shopId) {
		return bookingRepo.findBookingsByShopId(shopId);
	}
//	@Override
//	public List<Bookings> findByShopIdAndOrderStatusId(Integer shopId, Integer orderStatusId) {
//		return bookingRepo.findBookingsByShopIdAndOrderStatusId(shopId, orderStatusId);
//	}

	@Override
	@Transactional
	public void updateOrderStatus(String bookingId, Integer status) {
		Bookings booking = bookingRepo.findById(bookingId).orElse(null);
		if (booking != null) {
			booking.setOrderstatusid(status);
			bookingRepo.save(booking);
		}
	}

	@Override
	public List<Bookings> findByUserIdAndOrderStaturs(String userId, Integer orderstatusid) {
		return bookingRepo.findByUserIdAndOrderStaturs(userId,orderstatusid );
	}
}

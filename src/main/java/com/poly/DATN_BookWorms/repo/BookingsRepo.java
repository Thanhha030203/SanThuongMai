package com.poly.DATN_BookWorms.repo;




import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;


import com.poly.DATN_BookWorms.entities.Bookings;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.DATN_BookWorms.entities.Bookings;
import com.poly.DATN_BookWorms.entities.Cart;
import com.poly.DATN_BookWorms.entities.Shoponlines;
import org.springframework.data.repository.query.Param;


public interface BookingsRepo extends JpaRepository<Bookings, String>{
    @Query("SELECT o FROM Bookings o WHERE o.createat >= :startDate AND o.createat <= :endDate AND o.orderstatuses.orderstatusid = 5")
    List<Bookings> getIsSuccess(Date startDate, Date endDate);

	@Query("SELECT o FROM Bookings o WHERE o.createat >= :startDate AND o.createat <= :endDate AND o.orderstatuses.orderstatusid != 6 AND o.orderstatuses.orderstatusid !=7")
	List<Bookings> getIsPaid(Date startDate, Date endDate);

//	@Query("SELECT o FROM Bookings o WHERE o.account.username = ?1")
//	List<Bookings> findByUsername(String username);


	@Query("Select b from Bookings b where b.account.userid like ?1")
	List<Bookings> findBookingByUser(String userid);
	
	@Query("Select b.shoponlines from Books b where b.bookid in (Select c.books.bookid from Cart c where c.account.userid like ?1 )")
	List<Shoponlines> list_cart_shopId(String userid);

	@Query("Select b from Bookings b where b.orderstatusid = ?1")
	List<Bookings> ListBookings_Status(String orderStatusId);


	List<Bookings> findByuserid(String userId);

	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 1")
    long countUnpaid();
	
	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 2")
    long countPaid();
	
	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 3")
    long countConfirm();
	
	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 4")
    long countDelivering();
	
	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 5")
    long countProcessed();
	
	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 6")
    long countCancel();
	
	@Query("SELECT COUNT(b) FROM Bookings b WHERE b.orderstatuses.orderstatusid = 7")
    long countRefund();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 1")
	List<Bookings> unpaid();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 2")
	List<Bookings> paid();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 3")
	List<Bookings> confirm();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 4")
	List<Bookings> delivering();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 5")
	List<Bookings> processed();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 6")
	List<Bookings> cancel();
	
	@Query("SELECT b FROM Bookings b WHERE b.orderstatuses.orderstatusid = 7")
	List<Bookings> refund();
	@Query("SELECT b FROM Bookings b " +
			"INNER JOIN Detailbookings db ON b.bookingid = db.bookingid " +
			"INNER JOIN Books bk ON db.bookid = bk.bookid " +
			"WHERE bk.shopid = :shopid AND b.orderstatusid = :orderstatusid")
	List<Bookings> findBookingsByShopIdAndOrderStatusId(@Param("shopid") Integer shopId, @Param("orderstatusid") Integer orderStatusId);

	@Query("SELECT b FROM Bookings b " +
			"INNER JOIN Detailbookings db ON b.bookingid = db.bookingid " +
			"INNER JOIN Books bk ON db.bookid = bk.bookid " +
			"WHERE bk.shopid = :shopid")
	List<Bookings> findBookingsByShopId(@Param("shopid") Integer shopId);

	Bookings findBybookingid(String bookingId);
	@Query("SELECT b FROM Bookings b WHERE b.account.userid = ?1 and b.orderstatuses.orderstatusid = ?2")
    List<Bookings> findByUserIdAndOrderStaturs(String userId, Integer orderstatus);
}

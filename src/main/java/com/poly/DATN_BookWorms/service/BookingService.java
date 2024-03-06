package com.poly.DATN_BookWorms.service;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.DATN_BookWorms.entities.Account;
import com.poly.DATN_BookWorms.entities.Bookings;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

	Bookings create(JsonNode bookingData);

	Optional<Bookings> findById(String id);

	public List<Bookings> findByUserId(String userId);

	public List<Bookings> findAll();

	public List<Bookings> findByStatusId(String orderStatusId);

	long countUnpaid();

	long countPaid();

	long countConfirm();

	long countDelivering();

	long countProcessed();

	long countCancel();

	long countRefund();

	List<Bookings> unpaid();

	List<Bookings> paid();

	List<Bookings> confirm();

	List<Bookings> delivering();

	List<Bookings> processed();

	List<Bookings> cancel();

	List<Bookings> refund();

	Bookings findByBookingId(String bookingId);

	//	@Override
	//	public List<Bookings> findByUserIdAndOrderStatusId(String userId, Integer orderStatusId) {
	//		List<Bookings> allByUserId = findAllByUserId(userId);
	//
	//		return allByUserId.stream()
	//				.filter(booking -> booking.getOrderstatusid().equals(orderStatusId))
	//				.collect(Collectors.toList());
	//	}
	List<Bookings> findBookingsByShopIdAndOrderStatusID(Integer shopId, Integer orderStatusId);

	public Bookings update(Bookings bookingId);

	public Bookings byBookingUserId(String bookingUserId);
    @Transactional
	List<Bookings> findBookingsByShopId(Integer shopId);



	@Transactional
	void updateOrderStatus(String bookingId, Integer status);

    List<Bookings> findByUserIdAndOrderStaturs(String userId, Integer orderstatusid);
}

package com.flight.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flight.entity.BookingDetail;

@Service
public interface FlightBookingService {
	public List<BookingDetail> bookTicket(List<String> flightIds, List<Integer> passengerIds, Date bookingDate);

}

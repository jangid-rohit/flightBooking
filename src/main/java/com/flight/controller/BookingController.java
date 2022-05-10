package com.flight.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flight.entity.BookingDetail;
import com.flight.service.FlightBookingService;

@RestController
public class BookingController {

	@Autowired
	FlightBookingService fbs;

	@PostMapping("/bookTicket")
	public ResponseEntity<List<BookingDetail>> bookTicket(@RequestBody Map<String, Object> body) {
		List<String> flightIds = (List<String>) body.get("flightids");
		List<Integer> passengerIds = (List<Integer>) body.get("passengerids");

		final String bookingDateString = body.get("bookingDate").toString();
		Date bookingDate = null;
		try {
			bookingDate = new SimpleDateFormat("yyyy-MM-dd").parse(bookingDateString);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		List<BookingDetail> tickets = fbs.bookTicket(flightIds, passengerIds, bookingDate);
		return ResponseEntity.ok(tickets);
	}
}

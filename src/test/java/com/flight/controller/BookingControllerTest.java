package com.flight.controller;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.flight.entity.AirPlane;
import com.flight.entity.BookingDetail;
import com.flight.entity.FlightDetail;
import com.flight.entity.Passenger;
import com.flight.repository.BookingDetailRepository;
import com.flight.repository.PassengerRepository;
import com.flight.service.impl.FlightBookingServiceImpl;
@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

	@Mock
	private FlightBookingServiceImpl fbsi;
	@Mock
	private BookingDetailRepository bdr;
	@Mock
	private PassengerRepository pr;
	@InjectMocks
	private BookingController bc;

	@Test
	void bookTicket() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Map<String, Object> data = new HashMap<>();
		data.put("fligthids", List.of("2564"));
		data.put("passengerids", List.of(1));
		data.put("bookingDate", "2022-05-25");

		// Airplanes details
		AirPlane air1 = new AirPlane("123", "air india", 50);
		// Passenger details
		Passenger passenger1 = new Passenger(1, "rohit", 28, "1234255");
		// Flight details
		FlightDetail flight1 = new FlightDetail();
		flight1.setFlightNo("2564");
		flight1.setSource("c");
		flight1.setDestination("b");
		flight1.setDeparture(LocalTime.of(19, 50, 0));
		flight1.setArrival(LocalTime.of(21, 50, 0));
		flight1.setFair(2000);
		flight1.setAvailable(true);
		flight1.setPlane(air1);
		flight1.setDays("mon,tue,wed,fri,sat,sun");
		flight1.setAvailableSeat(50);
		List<String> flightids=new ArrayList<>();
		flightids.add("2564");
	//	when(fbsi.bookTicket(flightids,List.of(1), Date.valueOf("2022-05-25"))).thenReturn(null);
	ResponseEntity<List<BookingDetail>> bookTicket = bc.bookTicket(data);
	System.out.println(bookTicket);
	}

}

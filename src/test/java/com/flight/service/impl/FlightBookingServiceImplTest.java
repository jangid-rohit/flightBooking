package com.flight.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flight.entity.AirPlane;
import com.flight.entity.BookingDetail;
import com.flight.entity.FlightDetail;
import com.flight.entity.Passenger;
import com.flight.repository.BookingDetailRepository;
import com.flight.repository.FlightDetailRepository;
import com.flight.repository.PassengerRepository;

@ExtendWith(MockitoExtension.class)
class FlightBookingServiceImplTest {

	@Mock
	FlightDetailRepository fdr;
	@Mock
	BookingDetailRepository bdr;
	@Mock
	PassengerRepository pr;
	@InjectMocks
	FlightBookingServiceImpl fbsi;

	@Test
	void bookFlight() {
		// Airplanes details
		AirPlane air1 = new AirPlane("123", "air india", 50);
		AirPlane air2 = new AirPlane("650", "air asia", 35);
		AirPlane air3 = new AirPlane("125", "air india", 40);
		// Passenger details
		Passenger passenger1 = new Passenger(1, "rohit", 28, "1234255");
		Passenger passenger2 = new Passenger(2, "ishaan", 28, "2344255");
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

		FlightDetail flight2 = new FlightDetail();
		flight2.setFlightNo("3001");
		flight2.setSource("c");
		flight2.setDestination("a");
		flight2.setDeparture(LocalTime.of(15, 0, 0));
		flight2.setArrival(LocalTime.of(17, 0, 0));
		flight2.setFair(1000);
		flight2.setAvailable(true);
		flight2.setPlane(air2);
		flight2.setDays("mon,tue,wed,fri,sat,sun");
		flight2.setAvailableSeat(35);

		FlightDetail flight3 = new FlightDetail();
		flight3.setFlightNo("3002");
		flight3.setSource("a");
		flight3.setDestination("b");
		flight3.setDeparture(LocalTime.of(19, 0, 0));
		flight3.setArrival(LocalTime.of(21, 0, 0));
		flight3.setFair(1500);
		flight3.setAvailable(true);
		flight3.setPlane(air2);
		flight3.setDays("mon,tue,wed,fri,sat,sun");
		flight3.setAvailableSeat(35);

		FlightDetail flight4 = new FlightDetail();
		flight4.setFlightNo("3003");
		flight4.setSource("b");
		flight4.setDestination("d");
		flight4.setDeparture(LocalTime.of(23, 0, 0));
		flight4.setArrival(LocalTime.of(23, 32, 0));
		flight4.setFair(500);
		flight4.setAvailable(true);
		flight4.setPlane(air2);
		flight4.setDays("mon,tue,wed,fri,sat,sun");
		flight4.setAvailableSeat(35);

		List<FlightDetail> listOfFlights = new ArrayList<>();
		listOfFlights.add(flight2);
		listOfFlights.add(flight3);
		listOfFlights.add(flight1);
		listOfFlights.add(flight4);
		List<String> flightIds = List.of("2564");
		List<Integer> passengerIds = List.of(1);

		Date bookingDate = Date.valueOf("2022-05-25");
		when(fdr.findAllById(flightIds)).thenReturn(List.of(flight1));
		when(bdr.countByFlightDateAndFlight(bookingDate, flight1)).thenReturn(0);
		when(pr.findAllById(passengerIds)).thenReturn(List.of(passenger1));
		when(bdr.countByFlightDateAndFlightAndPassenger(bookingDate, flight1, passenger1)).thenReturn(0);

		BookingDetail bookingDetail = new BookingDetail(1, flight1, passenger1, bookingDate);
		BookingDetail exceptedDetail = bookingDetail;//new BookingDetail(1, flight1, passenger1, bookingDate);
		List<BookingDetail> exceptedTicket = new ArrayList<>();
		exceptedTicket.add(exceptedDetail);
		when(bdr.save(any(BookingDetail.class))).thenReturn(bookingDetail);
		List<BookingDetail> bookTicket = fbsi.bookTicket(flightIds, passengerIds, bookingDate);
		assertEquals(exceptedTicket, bookTicket);
	}
}

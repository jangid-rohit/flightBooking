package com.flight.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight.entity.BookingDetail;
import com.flight.entity.FlightDetail;
import com.flight.entity.Passenger;
import com.flight.repository.BookingDetailRepository;
import com.flight.repository.FlightDetailRepository;
import com.flight.repository.PassengerRepository;
import com.flight.service.FlightBookingService;

@Service
public class FlightBookingServiceImpl implements FlightBookingService{
	@Autowired
	FlightDetailRepository fdr;
	@Autowired
	BookingDetailRepository bdr;
	@Autowired
	PassengerRepository pr;

	public List<BookingDetail> bookTicket(List<String> flightIds, List<Integer> passengerIds, Date bookingDate) {

		if( flightIds == null || passengerIds == null ||bookingDate == null) {
			return Collections.emptyList();
		}
		List<BookingDetail> bookingDetails = new ArrayList<>();
		final int numberOfPassengers = passengerIds.size();

		Iterable<FlightDetail> flights = fdr.findAllById(flightIds);
		Iterator<FlightDetail> flightItr = flights.iterator();

		while (flightItr.hasNext()) {
			FlightDetail flight = flightItr.next();
			int count = bdr.countByFlightDateAndFlight(bookingDate, flight);
			int availableSeat = flight.getPlane().getTotalSeat() - count;
			if (availableSeat < numberOfPassengers) {
				return Collections.emptyList();
			}
			flight.setAvailableSeat(availableSeat);
		}
		Iterable<Passenger> passengers = pr.findAllById(passengerIds);

		passengers.forEach(passenger -> flights.forEach(flight -> {
			int alreadyBookedCount = bdr.countByFlightDateAndFlightAndPassenger(bookingDate, flight, passenger);
			if (alreadyBookedCount > 0) {
				return;
			}

			int aSeat = flight.getAvailableSeat();
			flight.setAvailableSeat(aSeat - 1);
			BookingDetail save = bdr.save(new BookingDetail(0, flight, passenger, bookingDate));
			bookingDetails.add(save);
		}));

		return bookingDetails;
	}
}

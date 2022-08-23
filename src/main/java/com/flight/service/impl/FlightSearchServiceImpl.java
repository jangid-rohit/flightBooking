package com.flight.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flight.entity.FlightDetail;
import com.flight.repository.BookingDetailRepository;
import com.flight.repository.FlightDetailRepository;
import com.flight.service.FlightSearchService;

/**
 * @author rohitsharma
 *
 */
@Service
public class FlightSearchServiceImpl implements FlightSearchService {
	@Autowired
	FlightDetailRepository fdr;

	@Autowired
	BookingDetailRepository bdr;

	/*
	 * This method find the all available flight seat including direct and
	 * connecting flights
	 */
	public Map<String, List<List<FlightDetail>>> searchFlights(String src, String des, Date date, int numberOfSeat) {

		// all direct and connecting flights will store in it.
		Map<String, List<List<FlightDetail>>> allAvailableFlights = new HashMap<>(2);
		// All connectingFlights
		List<List<FlightDetail>> connectingFlight = new ArrayList<>();
		// all direct flights
		List<List<FlightDetail>> directFlight = new ArrayList<>();
		allAvailableFlights.put("direct", directFlight);
		allAvailableFlights.put("connecting", connectingFlight);

		if (src == null || des == null || date == null) {
			return allAvailableFlights;
		}
		// this will return name of week,ex tue, wed, mon
		SimpleDateFormat weekFormat = new SimpleDateFormat("EEE");
		// store week in lowercase
		String week = weekFormat.format(date).toLowerCase();

		List<FlightDetail> allFlightDetails = fdr.findAllByDaysContainsOrderByDepartureAsc(week);
		if (allFlightDetails.isEmpty()) {
			return allAvailableFlights;
		}
		// This list contains only the flight which have same source
		List<FlightDetail> source = new ArrayList<>();
		extractDirectFlightAndRemoveUnwantedFlight(src, des, date, numberOfSeat, directFlight, allFlightDetails,
				source);
		allAvailableFlights.put("direct", directFlight);
		if (source.isEmpty()) {
			return allAvailableFlights;
		}
		extractConnectingFlights(des, connectingFlight, allFlightDetails, source);
		allAvailableFlights.put("connecting", connectingFlight);
		return allAvailableFlights;
	}

	/**
	 * @param src
	 * @param des
	 * @param date
	 * @param numberOfSeat
	 * @param directFlight
	 * @param allFlightDetails
	 * @param source
	 */
	private void extractDirectFlightAndRemoveUnwantedFlight(String src, String des, Date date, int numberOfSeat,
			List<List<FlightDetail>> directFlight, List<FlightDetail> allFlightDetails, List<FlightDetail> source) {
		for (Iterator<FlightDetail> flightDetail = allFlightDetails.iterator(); flightDetail.hasNext();) {
			FlightDetail flight = flightDetail.next();
			flight.setAvailableSeat(flight.getPlane().getTotalSeat());
			int numberOfBookedSeat = bdr.countByFlightDateAndFlight(date, flight);
			int availableSeat = flight.getAvailableSeat() - numberOfBookedSeat;
			if (availableSeat < numberOfSeat) {
				flightDetail.remove();
			} else {
				final String s = flight.getSource();
				final String d = flight.getDestination();
				if ((s.equals(src) && d.equals(des))) {
					directFlight.add(List.of(flight));
				} else if ((d.equals(src) || s.equals(des))) {
					flightDetail.remove();
				} else if (s.equals(src)) {
					source.add(flight);
				}
				flight.setAvailableSeat(availableSeat);
			}
		}
	}

	/*
	 * This method find the connectingFlight and if there then it will add those
	 * flights in the connectingFlight List
	 */
	private void extractConnectingFlights(String des, List<List<FlightDetail>> connectingFlight,
			List<FlightDetail> allFlightDetails, List<FlightDetail> source) {
		for (FlightDetail fd : source) {
			List<FlightDetail> tempList = new ArrayList<>();
			tempList.add(fd);
			FlightDetail haveIt = haveIt(allFlightDetails, fd.getDestination(), fd.getArrival());
			FlightDetail previous = null;
			while (haveIt != null) {
				if (!(previous != null && previous.getSource().equals(haveIt.getDestination())
						&& previous.getDestination().equals(haveIt.getSource()))) {
					tempList.add(haveIt);
				}

				if (haveIt.getDestination().equals(des)) {
					connectingFlight.add(tempList);
					break;
				}
				previous = haveIt;
				haveIt = haveIt(allFlightDetails, haveIt.getDestination(), haveIt.getArrival());

			}
		}
	}

	/*
	 * This method check if there is flight from src , according to the time or not
	 * if found then it return the flightDetail
	 */
	private FlightDetail haveIt(List<FlightDetail> listOfFlights, String src, LocalTime time) {
		ListIterator<FlightDetail> listIterator = listOfFlights.listIterator();
		while (listIterator.hasNext()) {
			FlightDetail next = listIterator.next();
			if (next.getSource().equals(src) && (next.getArrival().compareTo(time) > 0)) {
				return next;
			}
		}
		return null;
	}

}

package com.flight.service;

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

@Service
public class FlightSearchService {
	@Autowired
	FlightDetailRepository fdr;

	@Autowired
	BookingDetailRepository bdr;

	/*
	 * This method find the all available flight seat including direct and
	 * connecting flights
	 */
	public Map<String, List<List<FlightDetail>>> searchFlights(String src, String des, Date date, int numberOfSeat) {
		// this will return name of week,ex tue, wed, mon
		SimpleDateFormat weekFormat = new SimpleDateFormat("EEE");
		// store week in lowercase
		String week = weekFormat.format(date).toLowerCase();
		// all direct and connecting flights will store in it.
		Map<String, List<List<FlightDetail>>> allAvailableFlights = new HashMap<>(2);
		// All connectingFlights
		List<List<FlightDetail>> connectingFlight = new ArrayList<>();
		// all direct flights
		List<List<FlightDetail>> directFlight = new ArrayList<>();
		allAvailableFlights.put("direct", directFlight);
		allAvailableFlights.put("connecting", connectingFlight);
		List<FlightDetail> allFlightDetails = fdr.findAllByDaysContainsOrderByDepartureAsc(week);
		if (allFlightDetails.isEmpty()) {
			return allAvailableFlights;
		}

//This list contains only the flight which have same source
		List<FlightDetail> source = new ArrayList<>();

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

		allAvailableFlights.put("direct", directFlight);
		if (source.isEmpty()) {
			return allAvailableFlights;
		}

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
		allAvailableFlights.put("connecting", connectingFlight);
		return allAvailableFlights;
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

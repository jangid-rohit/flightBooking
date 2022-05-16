package com.flight.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.flight.entity.FlightDetail;

@Service
public interface FlightSearchService {
	public Map<String, List<List<FlightDetail>>> searchFlights(String src, String des, Date date, int numberOfSeat);

}

package com.flight.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.flight.entity.FlightDetail;
import com.flight.service.FlightSearchService;

@RestController
public class FlightSearchController {

	@Autowired
	FlightSearchService fss;
	
	@GetMapping("/s/{src}/{des}/{date}/{numberOfSeat}")
	public ResponseEntity<Map<String,List<List<FlightDetail>>>> searchFlight(@PathVariable String src, @PathVariable String des,
			@PathVariable Date date, @PathVariable int numberOfSeat) {
		Map<String, List<List<FlightDetail>>> connectingFlights = fss.searchFlights(src, des, date,numberOfSeat);
		return ResponseEntity.ok(connectingFlights);

	}
	
}

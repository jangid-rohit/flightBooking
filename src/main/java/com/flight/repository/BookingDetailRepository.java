package com.flight.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flight.entity.BookingDetail;
import com.flight.entity.FlightDetail;
import com.flight.entity.Passenger;

@Repository
public interface BookingDetailRepository extends CrudRepository<BookingDetail, Integer> {

	List<BookingDetail> findAll();

	int countByFlightDateAndFlight(Date date, FlightDetail flight);

	int countByFlightDateAndFlightAndPassenger(Date date, FlightDetail flight,Passenger passenger);
}

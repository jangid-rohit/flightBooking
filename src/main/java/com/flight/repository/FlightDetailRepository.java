package com.flight.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flight.entity.FlightDetail;

@Repository
public interface FlightDetailRepository extends CrudRepository<FlightDetail, String> {

	List<FlightDetail> findAll();
	
	
	
	List<FlightDetail> findAllByDaysContainsOrderByDepartureAsc(String days);

	List<FlightDetail> findBySourceAndDestinationAndDaysContains(String src, String des, String days);
}

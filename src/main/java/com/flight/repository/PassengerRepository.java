package com.flight.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flight.entity.Passenger;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
}

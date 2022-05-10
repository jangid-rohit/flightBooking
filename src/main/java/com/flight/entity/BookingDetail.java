package com.flight.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bookingdetail")
public class BookingDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	@ManyToOne
	@JoinColumn(name = "flight_no", nullable = false)
	private FlightDetail flight;
	@ManyToOne
	@JoinColumn(name = "passenger_id", nullable = false)
	private Passenger passenger;
	private Date flightDate;
	public BookingDetail() {}
	public BookingDetail(int bookingId, FlightDetail flight, Passenger passengerId, Date flightDate) {
		this.bookingId = bookingId;
		this.flight = flight;
		this.passenger = passengerId;
		this.flightDate = flightDate;
	}
	public int getBookingId() {
		return bookingId;
	}
	public FlightDetail getFlight() {
		return flight;
	}
	public Passenger getPassenger() {
		return passenger;
	}
	public Date getFlightDate() {
		return flightDate;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public void setFlight(FlightDetail flight) {
		this.flight = flight;
	}
	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	@Override
	public String toString() {
		return "BookingDetail [bookingId=" + bookingId + ", flight=" + flight + ", passenger=" + passenger
				+ ", flightDate=" + flightDate + "]";
	}
	
	
}

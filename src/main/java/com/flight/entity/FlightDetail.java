package com.flight.entity;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "flightdetail")
public class FlightDetail {

	@Id
	private String flightNo;
	private String source;
	private String destination;
	private LocalTime departure;
	private LocalTime arrival;
	private int fair;
	private boolean isAvailable;
	@ManyToOne
	@JoinColumn(name = "plane_id", nullable = false)
	private AirPlane plane;
	private String days;
	@Transient
	private int availableSeat;
		public String getFlightNo() {
		return flightNo;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public LocalTime getDeparture() {
		return departure;
	}

	public LocalTime getArrival() {
		return arrival;
	}

	public int getFair() {
		return fair;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public AirPlane getPlane() {
		return plane;
	}

	public String getDays() {
		return days;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setDeparture(LocalTime departure) {
		this.departure = departure;
	}

	public void setArrival(LocalTime arrival) {
		this.arrival = arrival;
	}

	public void setFair(int fair) {
		this.fair = fair;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setPlane(AirPlane plane) {
		this.plane = plane;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getAvailableSeat() {
		return availableSeat;
	}

	public void setAvailableSeat(int availableSeat) {
		this.availableSeat = availableSeat;
	}

	@Override
	public String toString() {
		return "FlightDetail [flightNo=" + flightNo + ", source=" + source + ", destination=" + destination
				+ ", departure=" + departure + ", arrival=" + arrival + ", fair=" + fair + ", isAvailable="
				+ isAvailable + ", plane=" + plane + ", days=" + days + ", availableSeat=" + availableSeat + "]";
	}

	


}

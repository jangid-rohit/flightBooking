package com.flight.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="airplane")
public class AirPlane {

	@Id
	@Column(name = "plane_id")
	private String planeId;
	private String company;
	private int totalSeat;
	
	public AirPlane() {}
	
	public AirPlane(String planeId, String company, int totalSeat) {
		this.planeId = planeId;
		this.company = company;
		this.totalSeat = totalSeat;
	}
	public String getPlaneId() {
		return planeId;
	}
	public void setPlaneId(String planeId) {
		this.planeId = planeId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getTotalSeat() {
		return totalSeat;
	}
	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}

	@Override
	public String toString() {
		return "AirPlane [planeId=" + planeId + ", company=" + company + ", totalSeat=" + totalSeat + "]";
	}
	
}

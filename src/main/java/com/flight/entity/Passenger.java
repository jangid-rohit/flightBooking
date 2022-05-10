package com.flight.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int passengerId;
	private String name;
	private int age;
	private String identity;
	public Passenger() {}
	public Passenger(int passengerId, String name, int age, String identity) {
		this.passengerId = passengerId;
		this.name = name;
		this.age = age;
		this.identity = identity;
	}
	public int getPassengerId() {
		return passengerId;
	}
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public String getIdentity() {
		return identity;
	}
	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	@Override
	public String toString() {
		return "Passenger [passengerId=" + passengerId + ", name=" + name + ", age=" + age + ", identity=" + identity
				+ "]";
	}
	
}

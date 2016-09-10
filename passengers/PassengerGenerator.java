package com.unimelb.swen30006.metromadness.passengers;

public interface PassengerGenerator {

	/**
	 * Generates passengers waiting at a station
	 * @return The list of generated passengers
	 */
	public Passenger[] generatePassengers();
	
	/**
	 * Generates a passenger waiting at a station
	 * @return The list of generated passengers
	 */
	public Passenger generatePassenger();
}

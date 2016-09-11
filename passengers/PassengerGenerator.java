package com.unimelb.swen30006.metromadness.passengers;

public interface PassengerGenerator {

	/**
	 * Generates passengers waiting at a station
	 * @return The list of generated passengers
	 */
	Passenger[] generatePassengers();
	
	/**
	 * Generates a passenger waiting at a station
	 * @return A single passenger which has been generated
	 */
	Passenger generatePassenger();
}

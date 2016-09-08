
package com.unimelb.swen30006.metromadness.routers;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * Passenger Router Interface
 */
public interface PassengerRouter {

	/**
	 * Checks if a passenger has reached their intended station and should disembark
	 * @param p the passenger
	 * @param current the station at which the passenger has arrived
	 * @return true if passenger should leave at the current station
	 */
	public boolean shouldLeave(Station current, Passenger p);
	
}

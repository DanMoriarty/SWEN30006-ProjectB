package com.unimelb.swen30006.metromadness.passengers;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * A representation of an everyday passenger on the Melbourne Metro
 */
public class Passenger {

	public Station begining;
	public Station destination;
	public float travelTime;
	public boolean reachedDestination;

	// Passenger routing logic
	public PassengerRouter router;

	public Passenger(Station start, Station end, PassengerRouter router){
		this.begining = start;
		this.destination = end;
		this.reachedDestination = false;
		this.travelTime = 0;
		this.router = router;
	}

	/**
	 * Updates passenger's travel time
	 * @param time the running travel time of the given passenger
	 */
	public void update(float time){
		if(!this.reachedDestination){
			this.travelTime += time;
		}
	}

	/**
 * Checks if passenger should leave the station that they're on
 * @param current the current station
 * @param passenger the passenger waiting to leave
 * @return true if this is the passenger's destination station
 */
	public boolean shouldLeave(Station current, Passenger passenger){
		return this.router.shouldLeave(current, passenger);
	}

}

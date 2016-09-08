package com.unimelb.swen30006.metromadness.passengers;

import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * A representation of an everyday passenger on the Melbourne Metro
 */
public class Passenger {

	public Station begining;
	public Station destination;
	public float travelTime;
	public boolean reachedDestination;
	
	public Passenger(Station start, Station end){
		this.begining = start;
		this.destination = end;
		this.reachedDestination = false;
		this.travelTime = 0;
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

	
	
}

package com.unimelb.swen30006.metromadness.passengers;

import java.util.ArrayList;
import java.util.Random;

import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * Program for generating passengers arriving at a station 
 * waiting to embark a train.
 *
 */
public class PassengerGenerator {
	
	// The station that passengers are getting on
	public Station station;
	// The line they are travelling on
	public ArrayList<Line> lines;
	
	// The max volume
	public float maxVolume;
	
	public PassengerGenerator(Station station, ArrayList<Line> lines, float max){
		this.station = station;
		this.lines = lines;
		this.maxVolume = max;
	}
	
	/**
	 * Randomly generates a list of passengers that spawn at a station
	 * @return a list of passengers
	 */
	public Passenger[] generatePassengers(){
		int count = (int) (Math.random()*maxVolume);
		Passenger[] passengers = new Passenger[count];
		for(int i=0; i<count; i++){
			passengers[i] = generatePassenger();
		}
		return passengers;
	}
	
	/**
	 * Randomly generates a single passenger for a station
	 * @return the newly created passenger
	 */
	public Passenger generatePassenger(){
		// Pick a random station from the line
		Line line = this.lines.get((int)Math.random()*(this.lines.size()-1));
		int current_station = line.stations.indexOf(this.station);
		boolean forward = Math.random()>0.5f;
		
		// If we are the end of the line then set our direction forward or backward
		if(current_station == 0){
			forward = true;
		}else if (current_station == line.stations.size()-1){
			forward = false;
		}
		
		// Find the station
		int index = 0;
		Random random = new Random();
		if (forward){
			index = random.nextInt(line.stations.size() -1-current_station) + current_station + 1;
		}else {
			index = current_station - 1 - random.nextInt(current_station);
		}
		Station s = line.stations.get(index);
		
		return this.station.generatePassenger(s);
	}
	
}

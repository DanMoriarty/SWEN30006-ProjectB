package com.unimelb.swen30006.metromadness.stations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.unimelb.swen30006.metromadness.exceptions.PlatformCapacityException;
import com.unimelb.swen30006.metromadness.exceptions.TrainCapacityException;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * A station that is active and thus able to take passengers from any
 * arriving trains
 */
public class ActiveStation extends Station implements PassengerGenerator {

	
	public ArrayList<Passenger> waiting;
	public float maxVolume;
	
	public ActiveStation(float x, float y, PassengerRouter router, String name, float maxPax) {
		super(x, y, router, name);
		this.waiting = new ArrayList<Passenger>();
		
		this.maxVolume = maxPax;
	}
	
	@Override
	public void enter(Train t) throws Exception {
		if(trains.size() >= PLATFORMS){
			throw new PlatformCapacityException();
		} else {
			// Add the train
			this.trains.add(t);
			// Add the waiting passengers
			Iterator<Passenger> pIter = this.waiting.iterator();
			while(pIter.hasNext()){
				Passenger p = pIter.next();
				try {
					t.embark(p);
					pIter.remove();
				} catch (TrainCapacityException e){
					// Do nothing, already waiting
					break;
				} catch (Exception e){
					e.printStackTrace();
					break;
				}
			}
			
			//Do not add new passengers if there are too many already
			if (this.waiting.size() > maxVolume){
				return;
			}
			// Add the new passenger
			Passenger[] ps = this.generatePassengers();
			for(Passenger p: ps){
				try {
					t.embark(p);
				} catch (TrainCapacityException e){
					this.waiting.add(p);
					break; 
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Passenger generatePassenger() {
		// Pick a random station from the line
		Line line = this.lines.get((int)Math.random()*(this.lines.size()-1));
		int current_station = line.stations.indexOf(this);
		boolean forward = Math.random()>0.5f;
				
		// If we are the end of the line then set our direction forward or backward
		forward = line.endOfLine(current_station, forward);
			
		// Find the destination station
		int index;
		Random random = new Random();
		if (forward){
			index = random.nextInt(line.stations.size() -1-current_station) + current_station + 1;
		}else {
			index = current_station - 1 - random.nextInt(current_station);
		}
		Station s = line.stations.get(index);
				
		return new Passenger(this, s, router);
	}


	@Override
	public Passenger[] generatePassengers() {
		int count = (int) (Math.random()*maxVolume);
		Passenger[] passengers = new Passenger[count];
		for(int i=0; i<count; i++){
			passengers[i] = generatePassenger();
		}
		return passengers;
	}

}

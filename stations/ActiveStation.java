package com.unimelb.swen30006.metromadness.stations;

import java.util.ArrayList;
import java.util.Iterator;

import com.unimelb.swen30006.metromadness.exceptions.PlatformCapacityException;
import com.unimelb.swen30006.metromadness.exceptions.TrainCapacityException;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.passengers.PassengerGenerator;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * A station that is active and thus able to take passengers from any
 * arriving trains
 */
public class ActiveStation extends Station {

	public PassengerGenerator g;
	public ArrayList<Passenger> waiting;
	public float maxVolume;
	
	public ActiveStation(float x, float y, PassengerRouter router, String name, float maxPax) {
		super(x, y, router, name);
		this.waiting = new ArrayList<Passenger>();
		this.g = new PassengerGenerator(this, this.lines, maxPax);
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
			Passenger[] ps = this.g.generatePassengers();
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

}

package com.unimelb.swen30006.metromadness.trains;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;

import com.unimelb.swen30006.metromadness.exceptions.*;

/**
 * Program representing a train running on the Melbourne Metro.
 * Handles a number of responsibilities depending on its given state as it
 * passes in and out of stations along its allocated line
 */
public class Train {

	// The state that a train can be in
	public enum State {
		IN_STATION, READY_DEPART, ON_ROUTE, WAITING_ENTRY, FROM_DEPOT
	}

	// Constants
	public static final Color FORWARD_COLOUR = Color.ORANGE;
	public static final Color BACKWARD_COLOUR = Color.VIOLET;
	public static final float TRAIN_WIDTH=4;
	public static final float TRAIN_SPEED=50f;

	// The line that this is traveling on
	public Line trainLine;

	// Passenger Information
	public ArrayList<Passenger> passengers;
	public float departureTimer;

	// Station and track and position information
	public Station station;
	public Track track;
	public Point2D.Float pos;

	// Direction and direction
	public boolean forward;
	public State state;

	// State variables
	public int numTrips;
	public boolean disembarked;


	public Train(Line trainLine, Station start, boolean forward){
		this.trainLine = trainLine;
		this.station = start;
		this.state = State.FROM_DEPOT;
		this.forward = forward;
		this.passengers = new ArrayList<Passenger>();
	}

	/**
	 * Update the simulation and train state
	 * @param delta a time step constant for simulation purposes
	 */
	public void update(float delta){
		// Update all passengers
		for(Passenger passenger: this.passengers){
			passenger.update(delta);
		}

		// Update the state
		switch(this.state) {
		case FROM_DEPOT:
			// We have our station initialized we just need to retrieve the next track, enter the
			// current station offically and mark as in station
			try {
				if(this.station.canEnter()){
					this.station.enter(this);
					this.pos = (Point2D.Float) this.station.position.clone();
					this.state = State.IN_STATION;
					this.disembarked = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		case IN_STATION:

			// When in station we want to disembark passengers
			// and wait 10 seconds for incoming passgengers
			if(!this.disembarked){
				this.disembark();
				this.departureTimer = this.station.getDepartureTime();
				this.disembarked = true;
			} else {
				// Count down if departure timer.
				if(this.departureTimer>0){
					this.departureTimer -= delta;
				} else {
					// We are ready to depart, find the next track and wait until we can enter
					try {
						boolean endOfLine = this.trainLine.endOfLine(this.station);
						if(endOfLine){
							this.forward = !this.forward;
						}
						this.track = this.trainLine.nextTrack(this.station, this.forward);
						this.state = State.READY_DEPART;
						break;
					} catch (UnlistedStationException e){
						e.printStackTrace();
					} catch (Exception e){
						// Massive error.
						e.printStackTrace();
					}
				}
			}
			break;
		case READY_DEPART:

			// When ready to depart, check that the track is clear and if
			// so, then occupy it if possible.
			if(this.track.canEnter(this.forward)){
				try {
					// Find the next
					Station next = this.trainLine.nextStation(this.station, this.forward);
					// Depart our current station
					this.station.depart(this);
					this.station = next;

				} catch (Exception e) {
					e.printStackTrace();
				}
				this.track.enter(this);
				this.state = State.ON_ROUTE;
			}
			break;
		case ON_ROUTE:

			// Checkout if we have reached the new station
			if(this.pos.distance(this.station.position) < 10 ){
				this.state = State.WAITING_ENTRY;
			} else {
				move(delta);
			}
			break;
		case WAITING_ENTRY:

			// Waiting to enter, we need to check the station has room and if so
			// then we need to enter, otherwise we just wait
			try {
				if(this.station.canEnter()){
					this.track.leave(this);
					this.pos = (Point2D.Float) this.station.position.clone();
					this.station.enter(this);
					this.state = State.IN_STATION;
					this.disembarked = false;
				}
			}
			catch (PlatformSizeException e) {
				//Train size is incompatible for the station's platform length
				try {
					//check if station is end of the line
					boolean endOfLine = this.trainLine.endOfLine(this.station);
					if(endOfLine){
						this.forward = !this.forward;
					}
					//get the next track
					this.track = this.trainLine.nextTrack(this.station, this.forward);

					this.state = State.READY_DEPART;
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

	}

	/**
	 * Renders train movement along its line
	 * @param delta the simulation time step constant
	 */
	public void move(float delta){
		// Work out where we're going
		float angle = angleAlongLine(this.pos.x,this.pos.y,this.station.position.x,this.station.position.y);
		float newX = this.pos.x + (float)( Math.cos(angle) * delta * TRAIN_SPEED);
		float newY = this.pos.y + (float)( Math.sin(angle) * delta * TRAIN_SPEED);
		this.pos.setLocation(newX, newY);
	}

	/**
	 * Removes passengers from a station and onto a train
	 * @param p
	 * @throws Exception if train type is incompatible with platform size
	 */
	public void embark(Passenger p) throws Exception {
		throw new Exception("Error: Train must be of type Big or Small");
	}

	/**
	 *Removes passengers from train as they reach their destination station
	 * @return list of passengers disembarking
	 */
	public ArrayList<Passenger> disembark(){
		ArrayList<Passenger> disembarking = new ArrayList<Passenger>();
		Iterator<Passenger> iterator = this.passengers.iterator();
		while(iterator.hasNext()){
			Passenger passenger = iterator.next();
			if(passenger.shouldLeave(this.station, passenger)){
				disembarking.add(passenger);
				iterator.remove();
			}
		}
		return disembarking;
	}

	@Override
	public String toString() {
		return "Train [line=" + this.trainLine.name +", departureTimer=" + departureTimer + ","
				+ " pos=" + pos + ", forward=" + forward + ", state=" + state
				+ ", numTrips=" + numTrips + ", disembarked=" + disembarked + "]";
	}

	/**
	 * Checks if this train is in a station
	 * @return true if train is in a station
	 */
	public boolean inStation(){
		return (this.state == State.IN_STATION || this.state == State.READY_DEPART);
	}

	public float angleAlongLine(float x1, float y1, float x2, float y2){
		return (float) Math.atan2((y2-y1),(x2-x1));
	}

	/**
	 * Renders the train
	 * @param renderer the elected graphics renderer
	 */
	public void render(ShapeRenderer renderer){
		if(!this.inStation()){
			Color col = this.forward ? FORWARD_COLOUR : BACKWARD_COLOUR;
			renderer.setColor(col);
			renderer.circle(this.pos.x, this.pos.y, TRAIN_WIDTH);
		}
	}



}

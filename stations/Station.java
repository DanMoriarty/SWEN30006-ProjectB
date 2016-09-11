package com.unimelb.swen30006.metromadness.stations;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.exceptions.TrainNotFoundException;
import com.unimelb.swen30006.metromadness.exceptions.PlatformCapacityException;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * A Station on the Melbourne Metro
 */
public class Station {
	
	//number of platforms in this station
	public static final int PLATFORMS=2;
	
	//rendering constants
	public Point2D.Float position;
	public static final float RADIUS=6;
	public static final int NUM_CIRCLE_STATMENTS=100;
	public static final int MAX_LINES=3;
	
	//station properties
	public String name;
	public ArrayList<Line> lines;
	public ArrayList<Train> trains;
	
	//passenger-related variables
	public static final float DEPARTURE_TIME = 2;
	public PassengerRouter router;

	public Station(float x, float y, PassengerRouter router, String name){
		this.name = name;
		this.router = router;
		this.position = new Point2D.Float(x,y);
		this.lines = new ArrayList<Line>();
		this.trains = new ArrayList<Train>();
	}
	
	/**
	 * Registers a train line that stops at this station
	 * @param l the train line
	 */
	public void registerLine(Line l){
		this.lines.add(l);
	}
	
	/**
	 * Renders the trains, station, and line
	 * @param renderer the graphics renderer chosen
	 */
	public void render(ShapeRenderer renderer){
		float radius = RADIUS;
		for(int i=0; (i<this.lines.size() && i<MAX_LINES); i++){
			Line l = this.lines.get(i);
			renderer.setColor(l.lineColour);
			renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);
			radius = radius - 1;
		}
		
		// Calculate the percentage
		float t = this.trains.size()/(float)PLATFORMS;
		Color c = Color.WHITE.cpy().lerp(Color.DARK_GRAY, t);
		renderer.setColor(c);
		renderer.circle(this.position.x, this.position.y, radius, NUM_CIRCLE_STATMENTS);
	}
	
	/**
	 * Registers a train entering a station, provided platforms are available
	 * @param t The train wishing to stop at this station
	 * @throws PlatformCapacityException If all platforms are currently occupied by other trains.
	 */
	public void enter(Train t) throws Exception {
		if(trains.size() >= PLATFORMS){
			throw new PlatformCapacityException();
		} else {
			this.trains.add(t);
		}
	}

	/**
	 * Registers a train wishing to leave a station, provided the track is available
	 * @param t The train wishing to leave this station
	 * @throws TrainNotFoundException If it checks the platforms but the train is not on any.
	 */
	public void depart(Train t) throws Exception {
		if(this.trains.contains(t)){
			this.trains.remove(t);
		} else {
			throw new TrainNotFoundException();
		}
	}
	
	/**
	 * Checks if a train can enter the station
	 * @return true if no platforms are occupied by other trains
	 */
	public boolean canEnter() {
		return trains.size() < PLATFORMS;
	}


	/**
	 * Returns departure time in seconds
	 * @return departure time
	 */
	public float getDepartureTime() {
		return DEPARTURE_TIME;
	}


	@Override
	public String toString() {
		return "Station [position=" + position + ", name=" + name + ", trains=" + trains.size()
				+ ", router=" + router + "]";
	}
}

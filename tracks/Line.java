package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.exceptions.UnlistedStationException;
import com.unimelb.swen30006.metromadness.stations.Station;

/**
 * A train line on the Melbourne Metro
 */

public class Line {
	
	// The colour of this line
	public Color lineColour;
	public Color trackColour;
	
	// The name of this line
	public String name;
	// The stations on this line
	public ArrayList<Station> stations;
	// The tracks on this line between stations
	public ArrayList<Track> tracks;
		
	// Create a line
	public Line(Color stationColour, Color lineColour, String name){
		// Set the line colour
		this.lineColour = stationColour;
		this.trackColour = lineColour;
		this.name = name;
		
		// Create the data structures
		this.stations = new ArrayList<Station>();
		this.tracks = new ArrayList<Track>();
	}
	
	/**
	 * Adds a station to the given line
	 * @param station the station to be added
	 * @param two_way flag that is true if the station has a dual track
	 */
	public void addStation(Station station, Boolean two_way){
		// We need to build the track if this is adding to existing stations
		if(this.stations.size() > 0){
			// Get the last station
			Station last = this.stations.get(this.stations.size()-1);
			
			// Generate a new track
			Track track;
			if(two_way){
				track = new DualTrack(last.position, station.position, this.trackColour);
			} else {
				track = new Track(last.position, station.position, this.trackColour);
			}
			this.tracks.add(track);
		}
		
		// Add the station
		station.registerLine(this);
		this.stations.add(station);
	}
	
	@Override
	public String toString() {
		return "Line [lineColour=" + lineColour + ", "
				+ "trackColour=" + trackColour + ", name=" + name + "]";
	}

	/**
	 * Checks if the current station is at the end of the line
	 * @param station the station to be checked
	 * @return true if this station is located at the end of the line
	 * @throws UnlistedStationException if the station does not belong
	 * to this line
	 */
	public boolean endOfLine(Station station) throws UnlistedStationException{
		if(this.stations.contains(station)){
			int index = this.stations.indexOf(station);
			return (index==0 || index==this.stations.size()-1);
		} else {
			throw new UnlistedStationException();
		}
	}

	
	/**
	 * Gets the next track for trains leaving or passing through stations
	 * @param currentStation the station a train is ready to leave
	 * @param forward true if the direction the train is travelling is forward
	 * @return the next track
	 * @throws UnlistedStationException if the current station does not beloong to
	 * this line
	 * @throws Exception in the event of an out of bounds query or other unintended occurrence
	 * so that program can record the error without halting operation
	 */
	public Track nextTrack(Station currentStation, boolean forward) throws UnlistedStationException, Exception {
		if(this.stations.contains(currentStation)){
			// Determine the track index
			int curIndex = this.stations.lastIndexOf(currentStation);
			// Increment to retrieve
			if(!forward){ curIndex -=1;}
				return this.tracks.get(curIndex);
		} else {
			throw new UnlistedStationException();
		}
	}
	
	/**
	 * Gets the next station for trains leaving or passing through the current station
	 * @param station the current station
	 * @param forward true if the direction the train is travelling is forward
	 * @return the next station to travel to
	 * @throws UnlistedStationException  if the current station does not beloong to
	 * this line
	 * @throws Exception in the event of an out of bounds query or other unintended occurrence
	 * so that program can record the error without halting operation
	 */
	public Station nextStation(Station station, boolean forward) throws UnlistedStationException, Exception{
		if(this.stations.contains(station)){
			int curIndex = this.stations.lastIndexOf(station);
			if(forward){ curIndex+=1;}else{ curIndex -=1;}
				return this.stations.get(curIndex);
		} else {
			throw new UnlistedStationException();
		}
	}
	
	/**
	 * Renders the line
	 * @param renderer the elected graphics renderer
	 */
	public void render(ShapeRenderer renderer){
		// Set the color to our line
		renderer.setColor(trackColour);
	
		// Draw all the track sections
		for(Track t: this.tracks){
			t.render(renderer);
		}	
	}
	
}

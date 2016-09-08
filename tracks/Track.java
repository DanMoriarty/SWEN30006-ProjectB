package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.trains.Train;

/**
 * A one-way section of track for a train to travel on
 */
public class Track {
	
	//rendering constants
	public static final float DRAW_RADIUS=10f;
	public static final int LINE_WIDTH=6;
	public Point2D.Float startPos;
	public Point2D.Float endPos;
	public Color trackColour;
	
	//track properties
	public boolean occupied;
	
	public Track(Point2D.Float start, Point2D.Float end, Color trackCol){
		this.startPos = start;
		this.endPos = end;
		this.trackColour = trackCol;
		this.occupied = false;
	}
	
	/**
	 * Renders the track
	 * @param renderer the elected graphics renderer
	 */
	public void render(ShapeRenderer renderer){
		renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, LINE_WIDTH);
	}
	
	/**
	 * Checks if there is track space for a train to enter
	 * @param forward true if the direction the train is travelling is forward
	 * @return true if their is space for an entering train
	 */
	public boolean canEnter(boolean forward){
		return !this.occupied;
	}
	
	/**
	 * Allows train to enter and marks track as occupied
	 * @param train the train entering
	 */
	public void enter(Train train){
		this.occupied = true;
	}
	
	@Override
	public String toString() {
		return "Track [startPos=" + startPos + ", endPos=" + endPos + ", "
				+ "trackColour=" + trackColour + ", occupied="
				+ occupied + "]";
	}

	/**
	 * Allows train to leave and marks track as not being occupied
	 * @param train
	 */
	public void leave(Train train){
		this.occupied = false;
	}
}

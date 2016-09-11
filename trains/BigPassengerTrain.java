package com.unimelb.swen30006.metromadness.trains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.exceptions.TrainCapacityException;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

/**
 * A larger passenger train on the Melbourne Metro that can take more
 * passengers but cannot stop at Short Platform Stations
 */
public class BigPassengerTrain extends Train {

	public BigPassengerTrain(Line trainLine, Station start, boolean forward) {
		super(trainLine, start, forward);
	}
	
	@Override
	/**
	 * @throws TrainCapacityException if the train has reached passenger capacity
	 */
	public void embark(Passenger p) throws TrainCapacityException {
		if(this.passengers.size() > 80){
			throw new TrainCapacityException();
		}
		this.passengers.add(p);
	}
	
	@Override
	public void render(ShapeRenderer renderer){
		if(!this.inStation()){
			Color col = this.forward ? FORWARD_COLOUR : BACKWARD_COLOUR;
			float percentage = this.passengers.size()/20f;
			renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
			renderer.circle(this.pos.x, this.pos.y, TRAIN_WIDTH*(1+percentage));
		}
	}

}

package com.unimelb.swen30006.metromadness.stations;

import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.SmallPassengerTrain;
import com.unimelb.swen30006.metromadness.trains.Train;

import com.unimelb.swen30006.metromadness.exceptions.*;

public class ShortPlatformStation extends ActiveStation {

	public ShortPlatformStation(float x, float y, PassengerRouter router, String name, float maxPax) {
		super(x, y, router, name, maxPax);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enter(Train t) throws PlatformSizeException, Exception {
		if (t.getClass()!=SmallPassengerTrain.class)
			throw new PlatformSizeException();
		else
			super.enter(t);
	}
	
	@Override
	public void depart(Train t) throws Exception{
		if (t.getClass()!=SmallPassengerTrain.class)
			return;
		super.depart(t);
	}
}

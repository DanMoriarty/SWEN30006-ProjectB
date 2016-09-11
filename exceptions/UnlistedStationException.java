package com.unimelb.swen30006.metromadness.exceptions;

public class UnlistedStationException extends Exception {

	public UnlistedStationException() {
		super("Error: Station is not part of this Line");
	}
	
}

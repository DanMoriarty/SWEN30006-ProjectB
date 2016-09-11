package com.unimelb.swen30006.metromadness.exceptions;

public class TrainNotFoundException extends Exception {

	public TrainNotFoundException() {
		super("Error: The train is not at this station");
	}

}

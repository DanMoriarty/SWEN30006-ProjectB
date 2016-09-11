package com.unimelb.swen30006.metromadness.exceptions;

public class TrainCapacityException extends Exception {

	public TrainCapacityException() {
		super("Error: Train capacity reached. Cannot take any more passengers");
	}

}

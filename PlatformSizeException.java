package com.unimelb.swen30006.metromadness.exceptions;

public class PlatformSizeException extends Exception{

	public PlatformSizeException() {
		super("Error: Train is too long for Platform");
	}

}

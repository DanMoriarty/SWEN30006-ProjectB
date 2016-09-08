package com.unimelb.swen30006.metromadness.exceptions;

public class EmptyPlatformException extends Exception {

	public EmptyPlatformException() {
		super("Error: No trains currently at any platform");
	}

}

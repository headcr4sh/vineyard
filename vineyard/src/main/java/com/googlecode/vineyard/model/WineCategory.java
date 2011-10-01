package com.googlecode.vineyard.model;

/**
 * 
 * @author Benjamin P. Jung
 */
public enum WineCategory {

	RED("Red"),

	WHITE("White"),

	ROSE("Rosé"),

	ORANGE("Orange");


	private final String stringValue;

	private WineCategory(final String stringValue) {
		this.stringValue = stringValue;
	}

	public String getStringValue() {
		return this.stringValue;
	}
}

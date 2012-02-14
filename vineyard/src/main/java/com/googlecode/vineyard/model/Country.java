package com.googlecode.vineyard.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * 
 * @author Benjamin P. Jung
 */
@XmlEnum
public enum Country {

	@XmlEnumValue(value = "ar")
	ARGENTINIA("Argentinia", "ar"),

	@XmlEnumValue(value = "au")
	AUSTRALIA("Australia", "au"),

	@XmlEnumValue(value = "at")
	AUSTRIA("Austria", "at"),

	@XmlEnumValue(value = "ca")
	CANADA("Canada", "ca"),

	@XmlEnumValue(value = "cl")
	CHILE("Chile", "cl"),

	@XmlEnumValue(value = "fr")
	FRANCE("France", "fr"),

	@XmlEnumValue(value = "de")
	GERMANY("Germany", "de"),

	@XmlEnumValue(value = "it")
	ITALY("Italy", "it"),

	@XmlEnumValue(value = "nz")
	NEW_ZEALAND("New Zealand", "nz"),

	@XmlEnumValue(value = "es")
	SPAIN("Spain", "es"),

	@XmlEnumValue(value = "tr")
	TURKEY("Turkey", "tr"),

	@XmlEnumValue(value = "za")
	SOUTH_AFRICA("South Africa", "za"),

	@XmlEnumValue(value = "us")
	UNITED_STATES("U.S.A.", "us");


	private final String name;
	private final String iso3166_1_alpha_2;
	private Country(final String name, String iso3166_1_alpha_2) {
		this.name = name;
		this.iso3166_1_alpha_2 = iso3166_1_alpha_2;
	}

	public String getName() {
		return this.name;
	}

	public String getIso3166_1_alpha_2() {
		return this.iso3166_1_alpha_2;
	}

}

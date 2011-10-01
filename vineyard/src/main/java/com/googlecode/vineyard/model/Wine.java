package com.googlecode.vineyard.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * 
 * @author Benjamin P. Jung
 */
@PersistenceCapable
public class Wine {

	/** Property Changes Support */
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);


	// Properties
	@Persistent private Vineyard vineyard;
	@Persistent private String name;
	@Persistent private int year;
	@Persistent private WineCategory category;
	@Persistent private BigDecimal priceEur;
	@Persistent private Rating rating;


	// ---- GETTERS AND SETTERS ------------------------------------------------

	public Vineyard getVineyard() {
		return vineyard;
	}

	public void setVineyard(Vineyard vineyard) {
		final Vineyard oldVineyard = this.vineyard;
		this.vineyard = vineyard;
		changeSupport.firePropertyChange("vineyard", oldVineyard, vineyard);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		final String oldName = this.name;
		this.name = name;
		changeSupport.firePropertyChange("name", oldName, name);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		final int oldYear = this.year;
		this.year = year;
		changeSupport.firePropertyChange("year", oldYear, year);
	}

	public WineCategory getCategory() {
		return category;
	}

	public void setCategory(WineCategory category) {
		final WineCategory oldCategory = this.category;
		this.category = category;
		changeSupport.firePropertyChange("category", oldCategory, category);
	}

	public BigDecimal getPriceEur() {
		return priceEur;
	}

	public void setPriceEur(BigDecimal priceEur) {
		final BigDecimal oldPriceEur = this.priceEur;
		this.priceEur = priceEur;
		changeSupport.firePropertyChange("priceEur", oldPriceEur, priceEur);
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		final Rating oldRating = this.rating;
		this.rating = rating;
		changeSupport.firePropertyChange("rating", oldRating, rating);
	}

	// ---- PROPERTY CHANGE SUPPORT --------------------------------------------

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

}

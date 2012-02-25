package com.googlecode.vineyard.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author Benjamin P. Jung
 */
@PersistenceCapable
public class Rating {

	/** Property Changes Support */
	private final PropertyChangeSupport changeSupport;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private Long ratingId;

	@Persistent private Integer taste;


	// c-tor
	public Rating() {
		super();
		changeSupport = new PropertyChangeSupport(this);
	}

	// ---- GETTERS AND SETTERS ------------------------------------------------

	public Integer getTaste() {
		return this.taste;
	}

	public void setTaste(Integer taste) {
		final Integer oldTaste = this.taste;
		this.taste = taste;
		changeSupport.firePropertyChange("taste", oldTaste, taste);
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

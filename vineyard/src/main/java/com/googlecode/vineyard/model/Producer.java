package com.googlecode.vineyard.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.jdo.annotations.Persistent;

/**
 * 
 * @author Benjamin P. Jung
 */
public class Producer {

	/** Property Changes Support */
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	@Persistent private String name;

	@Override
	public String toString() {
		return String.format("[Producer] %s", getName());
	}


	// ---- SETTERS AND GETTERS ------------------------------------------------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		final String oldName = this.name;
		this.name = name;
		changeSupport.firePropertyChange("name", oldName, name);
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

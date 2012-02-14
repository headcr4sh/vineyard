package com.googlecode.vineyard.model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.jdo.annotations.Persistent;

public class Grape {

	/** Property Changes Support */
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

	@Persistent private String name;
	@Persistent private String description;
	@Persistent private Color color;

	@Override
	public String toString() {
		return String.format("[Grape] %s", getName());
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		final String oldDescription = this.description;
		this.description = description;
		changeSupport.firePropertyChange("description", oldDescription, description);
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(final Color color) {
		final Color oldColor = this.color;
		this.color = color;
		changeSupport.firePropertyChange("color", oldColor, color);
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

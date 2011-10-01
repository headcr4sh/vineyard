package com.googlecode.vineyard.ui;

import java.awt.event.KeyEvent;

import javax.inject.Inject;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.googlecode.vineyard.model.Wine;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Benjamin P. Jung
 */
public class WineEditPanel extends JPanel {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -6332637653507545496L;

	// Underlying model
	private Wine wine;

	// UI components
	private JLabel typeLabel;
	private JComboBox typeComboBox;

	private JLabel nameLabel;
	private JTextField nameTextField;

	private JLabel yearLabel;
	private JTextField yearTextField;

	private JLabel ratingLabel;
	private JLabel ratingComponent;
	
	public WineEditPanel() {
		super(new MigLayout("insets panel", "[align trailing][align leading, grow, fill]"), true);
	}


	@Inject
	protected void postConstruct() {

		typeLabel = new JLabel();
		typeComboBox = new JComboBox();
		nameLabel = new JLabel();
		nameTextField = new JTextField();
		yearLabel = new JLabel();
		yearTextField = new JTextField();
		ratingLabel = new JLabel();
		ratingComponent = new JLabel("Not yet implemented");

		typeLabel.setText("Type");
		typeLabel.setDisplayedMnemonic(KeyEvent.VK_T);
		typeLabel.setLabelFor(typeComboBox);
		nameLabel.setText("Name");
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameTextField);
		yearLabel.setText("Year");
		yearLabel.setDisplayedMnemonic(KeyEvent.VK_Y);
		yearLabel.setLabelFor(yearTextField);
		ratingLabel.setText("Rating");
		ratingLabel.setDisplayedMnemonic(KeyEvent.VK_R);
		ratingLabel.setLabelFor(ratingComponent);

		add(typeLabel);
		add(typeComboBox, "wrap");
		add(nameLabel);
		add(nameTextField, "wrap");
		add(yearLabel);
		add(yearTextField, "wrap");
		add(ratingLabel);
		add(ratingComponent, "wrap");
		add(new JSeparator(), "growx, wrap");

	}


	// ---- SETTERS AND GETTERS ------------------------------------------------

	public Wine getWine() {
		return wine;
	}


	public void setWine(Wine wine) {
		final Wine oldWine = this.wine;
		this.wine = wine;
		firePropertyChange("wine", oldWine, wine);
	}

}

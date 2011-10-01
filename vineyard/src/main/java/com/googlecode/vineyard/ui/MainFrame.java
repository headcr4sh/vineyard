package com.googlecode.vineyard.ui;

import java.awt.BorderLayout;

import javax.inject.Inject;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;

/**
 * 
 * @author Benjamin P. Jung
 */
public class MainFrame extends JFrame {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = 7365980744906239688L;

	public MainFrame() {
		super();
	}

	@Inject
	protected void postConstruct() {

		// TODO Add WindowListener and handle exit event correctly
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Vineyard");
		setName("VineyardMainFrame");

	}

	public void setToolBar(final JToolBar toolBar) {
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public void setMainComponent(final JComponent component) {
		this.getContentPane().add(BorderLayout.CENTER, component);
	}

}

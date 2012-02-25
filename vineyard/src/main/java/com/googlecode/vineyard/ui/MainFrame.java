package com.googlecode.vineyard.ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import com.googlecode.vineyard.VineyardConfiguration;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class MainFrame extends JFrame {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = 7365980744906239688L;

	private final Preferences prefs = Preferences.userNodeForPackage(getClass());

	public MainFrame() {
		super();
		getRootPane().putClientProperty("apple.awt.brushMetalLook", Boolean.TRUE);
		getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.TRUE);
	}

	@Inject
	protected void postConstruct() {

		// TODO Add WindowListener and handle exit event correctly
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				final Rectangle frameBounds = MainFrame.this.getBounds();
				prefs.putInt("bounds.x", frameBounds.x);
				prefs.putInt("bounds.y", frameBounds.y);
				prefs.putInt("bounds.width", frameBounds.width);
				prefs.putInt("bounds.height", frameBounds.height);
			}
		});

		setTitle("Vineyard");
		setName("VineyardMainFrame");

	}

	public void setToolBar(final JToolBar toolBar) {
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public void setMainComponent(final JComponent component) {
		this.getContentPane().add(component, BorderLayout.CENTER);
	}

	public void setStatusBar(final JComponent component) {
		this.getContentPane().add(component, BorderLayout.SOUTH);
	}

}

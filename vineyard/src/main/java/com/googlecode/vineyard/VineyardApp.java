package com.googlecode.vineyard;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SwingUtilities;

import com.googlecode.vineyard.ui.MainFrame;
import com.googlecode.vineyard.ui.MenuBar;
import com.googlecode.vineyard.ui.WineListPanel;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class VineyardApp {

	@Inject MainFrame mainFrame;
	@Inject MenuBar menuBar;
	@Inject WineListPanel wineListPanel;

	public VineyardApp() {
		super();
	}

	public void start() {

		// Sets the name of the current thread.
		Thread.currentThread().setName("Vineyard");

		// Construct main frame
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setMainComponent(wineListPanel);
		

		// Show the main frame
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.pack();
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
	}

	public void stop() {

		// TODO Shutdown correctly
		System.exit(0);

	}

}

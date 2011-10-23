package com.googlecode.vineyard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jdo.PersistenceManager;
import javax.swing.SwingUtilities;

import com.googlecode.vineyard.model.Wine;
import com.googlecode.vineyard.ui.MainFrame;
import com.googlecode.vineyard.ui.MenuBar;
import com.googlecode.vineyard.ui.WineListPanel;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class VineyardApp {

	@Inject private VineyardConfiguration cfg;
	@Inject private PersistenceManager persistenceManager;

	@Inject private MainFrame mainFrame;
	@Inject private MenuBar menuBar;
	@Inject private WineListPanel wineListPanel;

	private Set<Listener> listeners;

	// Underlying model
	private List<Wine> wines;

	public VineyardApp() {
		super();
		listeners = new HashSet<Listener>();
		wines = new ArrayList<Wine>();
	}

	public void start() {

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

	public void addListener(Listener listener) {
		synchronized (listeners ) {
			listeners.add(listener);
		}
	}

	public void removeListener(Listener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void addWine(final Wine wine) {
		this.wines.add(wine);
		synchronized (listeners) {
			for (final Listener listener: listeners) {
				listener.onWineAdded(wine);
			}
		}
	}

	public void removeWine(final Wine wine) {
		this.wines.remove(wine);
		synchronized (listeners) {
			for (final Listener listener: listeners) {
				listener.onWineRemoved(wine);
			}
		}
	}

	public void updatedWine(final Wine wine) {
		assert wines.contains(wine);
		synchronized (listeners) {
			for (final Listener listener: listeners) {
				listener.onWineUpdated(wine);
			}
		}
	}

	// ---- INNER CLASSES ------------------------------------------------------

	public interface Listener {
		void onWineAdded(Wine wine);
		void onWineRemoved(Wine wine);
		void onWineUpdated(Wine wine);
	}

	public abstract class Adapter implements Listener {
		@Override
		public void onWineAdded(Wine wine) { /* ... */ }
		@Override
		public void onWineRemoved(Wine wine) { /* ... */ }
		@Override
		public void onWineUpdated(Wine wine) { /* ... */ }
	}

}

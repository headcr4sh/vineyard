package com.googlecode.vineyard;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.annotations.PersistenceAware;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.vineyard.model.Wine;
import com.googlecode.vineyard.ui.MainFrame;
import com.googlecode.vineyard.ui.MenuBar;
import com.googlecode.vineyard.ui.ToolBar;
import com.googlecode.vineyard.ui.WineListPanel;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
@PersistenceAware
public class VineyardApp {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject private VineyardConfiguration cfg;
	@Inject private PersistenceManager persistenceManager;

	@Inject private MainFrame mainFrame;
	@Inject private MenuBar menuBar;
	@Inject private ToolBar toolBar;
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

		// Show the main frame
		try {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				// Construct main frame
				//mainFrame.setJMenuBar(menuBar);
				mainFrame.setToolBar(toolBar);
				mainFrame.setMainComponent(wineListPanel);
				mainFrame.setPreferredSize(new Dimension(800, 600));
				mainFrame.pack();
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
		} catch (Exception e) {
			// TODO Proper error handling!
			logger.error(e.getLocalizedMessage(), e);
		}

		// Load all previously persisted objects
		for (final Wine wine: persistenceManager.getExtent(Wine.class, true)) {
			onWineAdded(wine);
		}

	}

	public void stop() {

		logger.info("Shutting down Vineyard app...");

		mainFrame.dispose();
		mainFrame = null;

		persistenceManager.close();
		persistenceManager = null;

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

		assert wine != null;

		final Transaction tx = persistenceManager.currentTransaction();
		try {
			tx.begin();
			persistenceManager.makePersistent(wine);
			tx.commit();
		} catch (final Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			if (tx.isActive()) {
				tx.rollback();
				return;
			}
		}

		onWineAdded(wine);

	}

	private final void onWineAdded(Wine wine) {

		assert wine != null;

		final int insertIdx = Collections.binarySearch(wines, wine);
		if (insertIdx < 0) {
			this.wines.add(-insertIdx - 1, wine);
		} else {
			throw new IllegalStateException("Wine does already exist!");
		}

		synchronized (listeners) {
			for (final Listener listener: listeners) {
				listener.onWineAdded(wine);
			}
		}

	}

	public void removeWine(final Wine wine) {

		onWineRemoved(wine);

		final Transaction tx = persistenceManager.currentTransaction();
		try {
			tx.begin();
			persistenceManager.deletePersistent(wine);
			tx.commit();
		} catch (final Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			if (tx.isActive()) {
				tx.rollback();
				return;
			}
		}

	}

	private final void onWineRemoved(final Wine wine) {

		assert wine != null;

		this.wines.remove(wine);
		synchronized (listeners) {
			for (final Listener listener: listeners) {
				listener.onWineRemoved(wine);
			}
		}
	}

	public void updateWine(final Wine wine) {

		final Transaction tx = persistenceManager.currentTransaction();
		try {
			tx.begin();
			persistenceManager.makePersistent(wine);
			tx.commit();
		} catch (final Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			if (tx.isActive()) {
				tx.rollback();
				return;
			}
		}

		onWineUpdated(wine);

	}

	private final void onWineUpdated(final Wine wine) {

		assert wine != null;
		assert wines.contains(wine);

		synchronized (listeners) {
			for (final Listener listener: listeners) {
				listener.onWineUpdated(wine);
			}
		}

	}

	public List<Wine> getWines() {
		return Collections.unmodifiableList(this.wines);
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

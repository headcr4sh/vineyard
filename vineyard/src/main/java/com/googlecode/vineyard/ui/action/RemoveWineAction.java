package com.googlecode.vineyard.ui.action;

import java.awt.event.ActionEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.googlecode.vineyard.VineyardApp;
import com.googlecode.vineyard.model.Wine;
import com.googlecode.vineyard.ui.EditWineDialogFactory;
import com.googlecode.vineyard.ui.EditWineDialogFactory.DialogType;
import com.googlecode.vineyard.ui.IconCache;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class RemoveWineAction extends AbstractAction {

	// Underlying model
	private Wine wine;

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -4135157974068598471L;

	@Inject private VineyardApp app;
	@Inject private IconCache iconCache;

	// c-tor
	public RemoveWineAction() {
		super();
		setWine(null);
	}

	@Inject
	protected void postConstruct() {
		putValue(NAME, "Remove wine...");
		putValue(SMALL_ICON, iconCache.getIcon("16x16/cross")); // FIXME
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		app.removeWine(wine);
	}

	public void setWine(final Wine wine) {
		this.wine = wine;
		setEnabled(wine != null);
	}

}

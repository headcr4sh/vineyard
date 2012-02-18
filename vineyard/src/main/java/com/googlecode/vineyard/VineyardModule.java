package com.googlecode.vineyard;

import javax.jdo.PersistenceManager;
import javax.swingx.StarRatingPanel;
import javax.swingx.StarRatingPanelProvider;
import javax.swingx.StarRatingTableCellEditor;
import javax.swingx.StarRatingTableCellRenderer;

import com.google.inject.AbstractModule;
import com.googlecode.vineyard.ui.WineRatingTableCellEditor;
import com.googlecode.vineyard.ui.WineRatingTableCellRenderer;

/**
 * Google Guice Injector module
 * @author Benjamin P. Jung
 */
public class VineyardModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(PersistenceManager.class).toProvider(VineyardPersistenceManagerProvider.class);
		bind(StarRatingPanel.class).toProvider(StarRatingPanelProvider.class);

		bind(StarRatingTableCellEditor.class).to(WineRatingTableCellEditor.class);
		bind(StarRatingTableCellRenderer.class).to(WineRatingTableCellRenderer.class);

	}

}

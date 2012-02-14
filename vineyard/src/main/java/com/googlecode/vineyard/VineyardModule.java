package com.googlecode.vineyard;

import javax.jdo.PersistenceManager;
import javax.swingx.StarRatingPanel;
import javax.swingx.StarRatingPanelProvider;

import com.google.inject.AbstractModule;

/**
 * GUICE Injector module
 * @author Benjamin P. Jung
 */
public class VineyardModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(PersistenceManager.class).toProvider(VineyardPersistenceManagerProvider.class);
		bind(StarRatingPanel.class).toProvider(StarRatingPanelProvider.class);

	}

}

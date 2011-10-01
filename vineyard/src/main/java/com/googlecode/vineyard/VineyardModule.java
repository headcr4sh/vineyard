package com.googlecode.vineyard;

import com.google.inject.AbstractModule;
import com.googlecode.vineyard.ui.IconCache;
import com.googlecode.vineyard.ui.MainFrame;
import com.googlecode.vineyard.ui.MenuBar;
import com.googlecode.vineyard.ui.WineListPanel;

/**
 * GUICE Injector module
 * @author Benjamin P. Jung
 */
public class VineyardModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(VineyardApp.class);

		bind(IconCache.class);

		bind(MainFrame.class);
		bind(MenuBar.class);
		bind(WineListPanel.class);

	}

}

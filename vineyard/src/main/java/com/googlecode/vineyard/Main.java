package com.googlecode.vineyard;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.vineyard.VineyardApp;
import com.googlecode.vineyard.VineyardModule;

class Main {

	public static void main(String[] args) {

		final Injector injector = Guice.createInjector(new VineyardModule());
		final VineyardApp app = injector.getInstance(VineyardApp.class);
		app.start();
	}

}
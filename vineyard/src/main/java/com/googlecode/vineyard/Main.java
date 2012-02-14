package com.googlecode.vineyard;

import java.io.IOException;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.vineyard.VineyardApp;
import com.googlecode.vineyard.VineyardModule;

/**
 * 
 * @author Benjamin P. Jung
 */
class Main {

	/** The Logger for this class */
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {

		// Sets the name of the current thread.
		Thread.currentThread().setName("Vineyard");

		logger.info("Adjusting system properties...");
		try {
			final Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("defaults.properties"));
			System.getProperties().putAll(props);
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (final IOException e) {
			logger.error("Failed to adjust system properties.");
			logger.error(e.getLocalizedMessage(), e);
			System.exit(1);
		}

		final Injector injector = Guice.createInjector(new VineyardModule());
		final VineyardApp app = injector.getInstance(VineyardApp.class);
		app.start();

	}

}
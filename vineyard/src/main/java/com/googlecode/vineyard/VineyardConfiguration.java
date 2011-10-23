package com.googlecode.vineyard;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class VineyardConfiguration {

	public static final String STORAGE_HOME_STR;
	static {
		STORAGE_HOME_STR = String.format("%s/%s", System.getProperty("user.home"), ".config/Vineyard");
	}

	/** Logger for the class */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private File storageHome;

	@Inject
	protected void postConstruct() {
		storageHome = new File(STORAGE_HOME_STR);
		if (!storageHome.exists()) {
			logger.info(String.format("Creating configuration storage directory: %s", STORAGE_HOME_STR));
			storageHome.mkdirs();
		}
	}

	public File getStorageHome() {
		return this.storageHome;
	}

}

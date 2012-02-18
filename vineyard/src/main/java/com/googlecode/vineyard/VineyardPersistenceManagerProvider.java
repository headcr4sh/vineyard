package com.googlecode.vineyard;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.jdo.JDOEnhancer;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class VineyardPersistenceManagerProvider implements Provider<PersistenceManager> {

	private File dbFile;
	private Properties properties;
	private PersistenceManagerFactory pmFactory;

	@Inject private VineyardConfiguration cfg;

	public VineyardPersistenceManagerProvider() {
		super();
		properties = new Properties();
	}

	@Inject
	protected void postConstruct() {

//		final JDOEnhancer enhancer = JDOHelper.getEnhancer();
//		enhancer.setVerbose(true);
//		enhancer.addPersistenceUnit("vineyard");
//		enhancer.enhance();

		try {
			dbFile = new File(cfg.getStorageHome(), "wines.db4o");
			final URL dbFileUrl = dbFile.toURI().toURL();
			properties.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
			properties.setProperty("datanucleus.ConnectionURL", String.format("db4o:%s", dbFileUrl.toExternalForm()));
			pmFactory = JDOHelper.getPersistenceManagerFactory(properties);
		} catch (final Exception ex) {
			// TODO Proper exception handling would be nice
			ex.printStackTrace();
		}

	}

	@Override
	public PersistenceManager get() {
		return pmFactory.getPersistenceManager();
	}
}

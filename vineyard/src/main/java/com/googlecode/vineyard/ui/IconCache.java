package com.googlecode.vineyard.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.vineyard.model.Country;
import com.googlecode.vineyard.model.WineCategory;

/**
 * Quick and Dirty implementation of a nice Icon Cache class for the
 * famfamfam icons
 *
 * @author Benjamin P. Jung
 */
@Singleton
public final class IconCache {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, IconCacheEntry> cache;

	// Default c-tor
	public IconCache() {
		cache = new HashMap<String, IconCacheEntry>();
	}

	public ImageIcon getIcon(final String name) {
		IconCacheEntry entry;
		if (cache.containsKey(name)) {
			entry = cache.get(name);
		} else {
			try {
				entry = new IconCacheEntry(name);
				cache.put(name, entry);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		return entry.icon16;

	}

	public Icon getMimetypeIcon(final String mimetype) {

		String iconName;

		if (mimetype == null) {
			iconName = "emoticon_unhappy";
		} else if (mimetype.equals("text/plain")) {
			iconName = "page_portrait";
		} else if (mimetype.equals("text/xml")) {
			iconName = "page_code";
		} else {
			iconName = "page_white";
		}
		return getIcon(iconName);

	}

	public Icon getIcon(final Country country) {
		String iconName = country.getIso3166_1_alpha_2();
		return getIcon(String.format("flags/%s", iconName));
	}

	public Image getImage(final String name) {
		return getIcon(name).getImage();
	}

	public Icon getIcon(final WineCategory wineCategory) {

		String iconName;

		switch (wineCategory) {
		case RED:
			iconName = "16x16/glass";
			break;
		case WHITE:
			iconName = "16x16/glass-white-liquid";
			break;
		case ROSE:
			iconName = "16x16/glass-pink-liquid";
			break;
		case ORANGE:
			iconName = "16x16/glass-orange-liquid";
			break;
		default:
			iconName = "16x16/glass-empty";
			break;
		}

		return getIcon(iconName);
	}

	/**
	 * Representation of a single entry in the icon cache
	 * @author Benjamin P. Jung
	 */
	private class IconCacheEntry {
		public final ImageIcon icon16;

		public IconCacheEntry(final String name) throws IOException {
			this.icon16 = new ImageIcon(createImage(name));
		}

		private Image createImage(String name) throws IOException {
			final ClassLoader cl = IconCache.class.getClassLoader();
			final String filename = String.format("icons/%s.png", name);
			try {
				return ImageIO.read(cl.getResourceAsStream(filename));
			} catch (final IllegalArgumentException e) {
				logger.error(String.format("File not found: %s", filename));
				throw e;
			}

		}

	}

}

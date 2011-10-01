package com.googlecode.vineyard.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Quick and Dirty implementation of a nice Icon Cache class for the
 * famfamfam icons
 *
 * @author Benjamin P. Jung
 */
@Singleton
public final class IconCache {

	private final Map<String, IconCacheEntry> cache;

	// Default c-tor
	public IconCache() {
		cache = new HashMap<String, IconCacheEntry>();
	}


	public Icon getIcon(final String name) {
		IconCacheEntry entry;
		if (cache.containsKey(cache)) {
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

	/**
	 * Representation of a single entry in the icon cache
	 * @author Benjamin P. Jung
	 */
	private class IconCacheEntry {
		public final Icon icon16;

		public IconCacheEntry(final String name) throws IOException {
			this.icon16 = new ImageIcon(createImage(name));
		}

		private Image createImage(String name) throws IOException {
			final ClassLoader cl = IconCache.class.getClassLoader();
			final String filename = String.format("icons/%s.png", name);
			try {
				return ImageIO.read(cl.getResourceAsStream(filename));
			} catch (final IllegalArgumentException e) {
				throw e;
			}

		}

	}

}

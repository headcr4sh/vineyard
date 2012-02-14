package com.googlecode.vineyard.ui;

import java.awt.Component;

import javax.inject.Inject;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import com.googlecode.vineyard.model.Country;

/**
 * 
 * @author Benjamin P. Jung
 */
public class CountryComboBox extends JComboBox {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -304509487711054529L;

	@Inject IconCache iconCache;

	public CountryComboBox() {
		super(Country.values());
		setRenderer(new WineCategoryCellRenderer());
	}

	// ---- INNER CLASSES ------------------------------------------------------

	private class WineCategoryCellRenderer extends DefaultListCellRenderer {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = -7813701864731257961L;

		@Override
		public Component getListCellRendererComponent(JList arg0, Object object, int arg2, boolean arg3, boolean arg4) {

			super.getListCellRendererComponent(arg0, object, arg2, arg3, arg4);

			final Country country = (Country) object;
			setText(country.getName());
			setIcon(iconCache.getIcon(country));
			return this;
		}

		
	}
}

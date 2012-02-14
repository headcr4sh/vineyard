package com.googlecode.vineyard.ui;

import java.awt.Component;

import javax.inject.Inject;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import com.googlecode.vineyard.model.WineCategory;

/**
 * 
 * @author Benjamin P. Jung
 */
public class WineCategoryComboBox extends JComboBox {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = 6673241031601203862L;

	@Inject IconCache iconCache;

	public WineCategoryComboBox() {
		super(WineCategory.values());
		setRenderer(new WineCategoryCellRenderer());
	}


	// ---- INNER CLASSES ------------------------------------------------------

	private class WineCategoryCellRenderer extends DefaultListCellRenderer {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = -7813701864731257961L;

		@Override
		public Component getListCellRendererComponent(JList arg0, Object object, int arg2, boolean arg3, boolean arg4) {

			super.getListCellRendererComponent(arg0, object, arg2, arg3, arg4);

			final WineCategory category = (WineCategory) object;
			setText(category.getStringValue());
			setIcon(iconCache.getIcon(category));
			return this;
		}

		
	}
}

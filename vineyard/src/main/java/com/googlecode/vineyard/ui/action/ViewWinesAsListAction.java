package com.googlecode.vineyard.ui.action;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.googlecode.vineyard.ui.IconCache;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class ViewWinesAsListAction extends AbstractAction {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -4135157974068598471L;

	@Inject private IconCache iconCache;

	// c-tor
	public ViewWinesAsListAction() {
		super();
	}

	@Inject
	protected void postConstruct() {
		putValue(NAME, "View as list");
		if (System.getProperty("os.name").contains("Mac OS X")) {
			putValue(SMALL_ICON, new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSListViewTemplate"))); 
		} else {
			putValue(SMALL_ICON, iconCache.getIcon("16x16/edit-alignment-justify"));
		}
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		// TODO Implement
	}

}

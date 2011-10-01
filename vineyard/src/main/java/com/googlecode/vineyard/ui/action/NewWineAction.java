package com.googlecode.vineyard.ui.action;

import java.awt.event.ActionEvent;

import javax.inject.Inject;
import javax.swing.AbstractAction;

import com.googlecode.vineyard.ui.IconCache;

/**
 * 
 * @author Benjamin P. Jung
 */
public class NewWineAction extends AbstractAction {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -2600248882982285644L;

	@Inject private IconCache iconCache;

	@Inject
	protected void postConstruct() {
		putValue(NAME, "Add new wine...");
		putValue(SMALL_ICON, iconCache.getIcon("page_add"));
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
	}

}

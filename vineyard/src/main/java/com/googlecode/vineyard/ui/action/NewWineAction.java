package com.googlecode.vineyard.ui.action;

import java.awt.event.ActionEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.googlecode.vineyard.ui.EditWineDialogFactory;
import com.googlecode.vineyard.ui.EditWineDialogFactory.DialogType;
import com.googlecode.vineyard.ui.IconCache;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class NewWineAction extends AbstractAction {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -2600248882982285644L;

	@Inject EditWineDialogFactory dialogFactory;
	@Inject private IconCache iconCache;

	@Inject
	protected void postConstruct() {
		putValue(NAME, "Add new wine...");
		putValue(SMALL_ICON, iconCache.getIcon("16x16/plus"));
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		final JDialog dialog = dialogFactory.createDialog(DialogType.CREATE, null);
		dialog.setVisible(true);
	}

}

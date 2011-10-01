package com.googlecode.vineyard.ui;

import java.awt.event.KeyEvent;

import javax.inject.Inject;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.googlecode.vineyard.ui.action.NewWineAction;

public class MenuBar extends JMenuBar {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -428232672892548032L;

	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu helpMenu;

	@Inject NewWineAction newWineAction;

	public MenuBar() {
		super();
	}

	@Inject
	protected void postConstruct() {

		fileMenu = new JMenu();
		fileMenu.setText("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		editMenu = new JMenu();
		editMenu.setText("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		viewMenu = new JMenu();
		viewMenu.setText("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);

		helpMenu = new JMenu();
		helpMenu.setText("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		fileMenu.add(newWineAction);
		
		this.add(fileMenu);
		this.add(editMenu);
		this.add(viewMenu);
		this.add(helpMenu);

	}

}

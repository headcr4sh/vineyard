package com.googlecode.vineyard.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

import com.google.inject.Injector;
import com.googlecode.vineyard.VineyardApp;
import com.googlecode.vineyard.model.Wine;

/**
 * 
 * @author Benjamin P. Jung
 */
@Singleton
public class EditWineDialogFactory implements java.io.Serializable {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -1379652633271982785L;

	@Inject private Injector injector;
	@Inject private VineyardApp app;
	@Inject private MainFrame mainFrame;
	@Inject private IconCache iconCache;

	// c-tor
	public EditWineDialogFactory() {
		super();
	}

	public JDialog createDialog(final DialogType type, final Wine wine) {

		final JDialog dialog = new JDialog(mainFrame, true);
		dialog.getRootPane().putClientProperty("Window.style", "small");
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		switch (type) {
		case CREATE:
			dialog.setTitle("Add new wine...");
			break;
		case EDIT:
			dialog.setTitle("Edit wine...");
			break;
		default:
			throw new IllegalArgumentException();
		}
		
		final JPanel contentPane = new JPanel(new MigLayout(
				"insets null",
				"[grow, fill]",
				"[grow, fill]u[]u[]"
		));

		final EditWinePanel editPanel = injector.getInstance(EditWinePanel.class);
		editPanel.setWine(wine == null ? new Wine() : wine);
		contentPane.add(editPanel, "growx, span, wrap");
		contentPane.add(new JSeparator(SwingConstants.HORIZONTAL), "growx, span, wrap");
		final JButton helpButton = new JButton();
		helpButton.putClientProperty("JButton.buttonType", "help");
		helpButton.setEnabled(false); // TODO Implement help
		final JButton okButton = new JButton(new OkAction(type, dialog, editPanel));
		final JButton cancelButton = new JButton(new CancelAction(dialog));
		final JPanel buttonPanel = new JPanel(new MigLayout("ins 0", "[grow]u[]r[]"));

		buttonPanel.add(helpButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(okButton);
		contentPane.add(buttonPanel);
		dialog.getContentPane().add(contentPane, BorderLayout.CENTER);

		SwingUtilities.updateComponentTreeUI(dialog);
		dialog.pack();
		dialog.setLocationRelativeTo(mainFrame);

		return dialog;

	}


	public static enum DialogType {
		CREATE,
		EDIT;
	}


	private final class OkAction extends AbstractAction {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = -4705544712369129336L;

		private final DialogType dialogType;
		private final JDialog dialog;
		private final EditWinePanel editPanel;

		private OkAction(final DialogType dialogType, final JDialog dialog, final EditWinePanel editPanel) {
			this.dialogType = dialogType;
			this.dialog = dialog;
			this.editPanel = editPanel;
			putValue(NAME, "Ok");
			putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_O));
			putValue(SMALL_ICON, iconCache.getIcon("16x16/tick"));
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			editPanel.updateWine();
			final Wine wine = editPanel.getWine();
			switch (dialogType) {
			case CREATE:
				app.addWine(wine);
				break;
			case EDIT:
				app.updateWine(wine);
				break;
			default:
				throw new IllegalStateException();
			}

			dialog.setVisible(false);
			dialog.dispose();

		}

	}

	private final class CancelAction extends AbstractAction {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = -4557314340562797237L;

		private final JDialog dialog;

		private CancelAction(final JDialog dialog) {
			this.dialog = dialog;
			putValue(NAME, "Cancel");
			putValue(MNEMONIC_KEY, Integer.valueOf(KeyEvent.VK_C));
			putValue(SMALL_ICON, iconCache.getIcon("16x16/cross"));
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			dialog.setVisible(false);
			dialog.dispose();
		}

	}

}

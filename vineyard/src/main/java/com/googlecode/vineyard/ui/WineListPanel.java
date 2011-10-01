package com.googlecode.vineyard.ui;

import java.awt.Component;

import javax.inject.Inject;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.googlecode.vineyard.VineyardApp;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Benjamin P. Jung
 */
public class WineListPanel extends JPanel {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -3815816196000256409L;

	@Inject VineyardApp app;
	@Inject IconCache iconCache;

	// UI components
	private TableModel tableModel;
	private JTable table;
	
	public WineListPanel() {
		super(new MigLayout("insets panel, align leading, fill", "[fill]", "[fill]"), true);
	}

	@Inject
	protected void postConstruct() {

		tableModel = new TableModel();
		table = new JTable(tableModel);

		// Attaches the icon cell renderer
		final TableColumn iconColumn = table.getColumnModel().getColumn(0);
		iconColumn.setMaxWidth(20);
		iconColumn.setResizable(false);
		iconColumn.setCellRenderer(new ImageRenderer());

		this.add(new JScrollPane(table), "grow, wrap");
	}


	// ---- INNER CLASSES ------------------------------------------------------

	private class TableModel extends AbstractTableModel {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = 33717013110614979L;

		public String[] columnNames = new String[] {
			"T", "Name", "Year", "Rating"
		};

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {

			switch (columnIndex) {
			case 0:
				return iconCache.getIcon("bullet_red");
			case 1:
				return "xxx";
			case 2:
				return "xxx";
			case 3:
				return "xxx";
			default:
				throw new IllegalStateException();
			}

		}
		
	}

	/**
	 * 
	 * @author Benjamin P. Jung
	 */
	private class ImageRenderer extends DefaultTableCellRenderer {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = -8812307157083949226L;

		private final JLabel lbl = new JLabel();

		private ImageRenderer() {
			super();
			lbl.setIconTextGap(0);
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			lbl.setIcon((Icon) value);
			return lbl;
		}
	}
}

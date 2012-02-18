package com.googlecode.vineyard.ui;

import java.awt.Component;

import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swingx.StarRatingPanel;
import javax.swingx.StarRatingTableCellEditor;
import javax.swingx.StarRatingTableCellRenderer;

import com.googlecode.vineyard.VineyardApp;
import com.googlecode.vineyard.model.Wine;
import com.googlecode.vineyard.ui.action.EditWineAction;
import com.googlecode.vineyard.ui.action.RemoveWineAction;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Benjamin P. Jung
 */
public class WineListPanel extends JPanel implements VineyardApp.Listener {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -3815816196000256409L;

	public static final int CATEGORY_INDEX = 0;
	public static final int NAME_INDEX = 1;
	public static final int PRODUCER_INDEX = 2;
	public static final int COUNTRY_INDEX = 3;
	public static final int YEAR_INDEX = 4;
	public static final int RATING_INDEX = 5;

	public static final String[] COLUMN_NAMES = new String[6];
	static {
		COLUMN_NAMES[CATEGORY_INDEX] = "C";
		COLUMN_NAMES[NAME_INDEX] = "Name";
		COLUMN_NAMES[PRODUCER_INDEX] = "Producer";
		COLUMN_NAMES[COUNTRY_INDEX] = "Country";
		COLUMN_NAMES[YEAR_INDEX] = "Year";
		COLUMN_NAMES[RATING_INDEX] = "Rating";
	}

	@Inject private VineyardApp app;
	@Inject private IconCache iconCache;
	@Inject private StarRatingTableCellEditor starRatingCellEditor;
	@Inject private StarRatingTableCellRenderer starRatingCellRenderer;
	@Inject private EditWineAction editWineAction;
	@Inject private RemoveWineAction removeWineAction;

	// UI components
	private TableModel tableModel;
	private JTable table;
	
	public WineListPanel() {
		super(new MigLayout("ins 0, align leading, fill", "[fill]", "[fill]"), true);
	}

	@Inject
	protected void postConstruct() {

		tableModel = new TableModel();
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Attaches the icon cell renderer
		final TableColumnModel columnModel = table.getColumnModel();
		final TableColumn iconColumn = columnModel.getColumn(CATEGORY_INDEX);
		final TableColumn nameColumn = columnModel.getColumn(NAME_INDEX);
		final TableColumn producerColumn = columnModel.getColumn(PRODUCER_INDEX);
		final TableColumn countryColumn = columnModel.getColumn(COUNTRY_INDEX);
		final TableColumn yearColumn = columnModel.getColumn(YEAR_INDEX);
		final TableColumn ratingColumn = columnModel.getColumn(RATING_INDEX);

		final CellRenderer cellRenderer = new CellRenderer();

		iconColumn.setMaxWidth(20);
		iconColumn.setResizable(false);
		iconColumn.setCellRenderer(cellRenderer);
		nameColumn.setCellRenderer(cellRenderer);
		producerColumn.setCellRenderer(cellRenderer);
		countryColumn.setCellRenderer(cellRenderer);
		yearColumn.setMaxWidth(60); // FIXME Calculate width correctly!
		yearColumn.setResizable(false);
		yearColumn.setCellRenderer(cellRenderer);
		ratingColumn.setMaxWidth(StarRatingPanel.DEFAULT_NUM_STARS * 16);
		ratingColumn.setResizable(false);
		ratingColumn.setCellRenderer(starRatingCellRenderer);
		ratingColumn.setCellEditor(starRatingCellEditor);

		this.add(new JScrollPane(table), "grow");

		// Attaches a listener to the table
		final SelectionListener selectionListener = new SelectionListener();
		table.getSelectionModel().addListSelectionListener(selectionListener);
		//table.getColumnModel().getSelectionModel().addListSelectionListener(selectionListener);

		// Add as listener to the app
		app.addListener(this);

	}

	// ---- INNER CLASSES ------------------------------------------------------

	private class TableModel extends AbstractTableModel {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = 33717013110614979L;

		@Override
		public String getColumnName(int columnIndex) {
			return COLUMN_NAMES[columnIndex];
		}

		@Override
		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		@Override
		public int getRowCount() {
			return app.getWines().size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {

			final Wine wine = app.getWines().get(rowIndex);
			return wine;

		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return column == RATING_INDEX;
		}

	}

	/**
	 * 
	 * @author Benjamin P. Jung
	 */
	private class CellRenderer extends DefaultTableCellRenderer {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = -8812307157083949226L;

		private CellRenderer() {
			super();
			setIconTextGap(0);
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			final Wine wine = (Wine) value;

			switch (column) {
			case CATEGORY_INDEX:
				setIcon(iconCache.getIcon(wine.getCategory()));
				setIconTextGap(0);
				setHorizontalAlignment(SwingConstants.CENTER);
				setText(null);
				break;
			case NAME_INDEX:
				setIcon(null);
				setIconTextGap(0);
				setHorizontalAlignment(SwingConstants.LEADING);
				setText(wine.getName());
				break;
			case PRODUCER_INDEX:
				setIcon(null);
				setIconTextGap(0);
				setHorizontalAlignment(SwingConstants.LEADING);
				setText(wine.getProducer());
				break;
			case COUNTRY_INDEX:
				setIcon(iconCache.getIcon(wine.getCountry()));
				setIconTextGap(8);
				setHorizontalAlignment(SwingConstants.LEADING);
				setText(wine.getCountry().getName());
				break;
			case YEAR_INDEX:
				setIcon(null);
				setIconTextGap(0);
				setHorizontalAlignment(SwingConstants.LEADING);
				setText(String.valueOf(wine.getYear()));
				break;
			case RATING_INDEX:
				return null;
			default:
				throw new IllegalStateException();
			}
			
			return this;
		}

	}

	@Override
	public void onWineAdded(Wine wine) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final int numWines = app.getWines().size();
				tableModel.fireTableRowsInserted(numWines-1, numWines-1);
			}
		});
	}

	@Override
	public void onWineRemoved(Wine wine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWineUpdated(Wine wine) {
		// TODO Auto-generated method stub
		
	}


	private class SelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				final int idx = table.getSelectedRow();
				final Wine wine = (Wine) table.getValueAt(idx, 0);
				editWineAction.setWine(wine);
				removeWineAction.setWine(wine);
			}
		}

	}

}

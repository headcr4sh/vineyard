package javax.swingx;

import java.awt.Color;
import java.awt.Component;

import javax.inject.Inject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

/**
 * 
 * @author Benjamin P. Jung
 */
public abstract class StarRatingTableCellEditor extends AbstractCellEditor implements TableCellEditor {

	@Inject private StarRatingPanel starRatingPanel;
	
	/** @see java.io.Serializable */
	private static final long serialVersionUID = -5087207446556772886L;

	@Inject
	protected void postConstruct() {
		starRatingPanel.setOpaque(true);
	}

	@Override
	public Object getCellEditorValue() {
		return Integer.valueOf(starRatingPanel.getSelected());
	}

	protected abstract int getRating(Object value);

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		final Color bgColor = UIManager.getColor("Table.selectionBackground");
		starRatingPanel.setBackground(bgColor);
		starRatingPanel.setSelected(getRating(value));
		return starRatingPanel;
	}

}

package javax.swingx;

import java.awt.Color;
import java.awt.Component;

import javax.inject.Inject;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Benjamin P. Jung
 */
public abstract class StarRatingTableCellRenderer implements TableCellRenderer {

	@Inject private StarRatingPanel starRatingPanel;

	@Inject
	protected void postConstruct() {
		starRatingPanel.setOpaque(true);
		starRatingPanel.setSelected(0);
	}

	protected abstract int getRating(Object value);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		final Color bgColor = isSelected ? UIManager.getColor("Table.selectionBackground") : UIManager.getColor("Table.background");
		starRatingPanel.setBackground(bgColor);
		starRatingPanel.setSelected(getRating(value));
		return starRatingPanel;

	}

}

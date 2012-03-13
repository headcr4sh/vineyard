package javax.swingx;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

	protected Object value;

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -5087207446556772886L;

	public StarRatingTableCellEditor() {
		super();
	}
	
	@Inject
	protected void postConstruct() {
		starRatingPanel.setOpaque(true);
		starRatingPanel.addPropertyChangeListener("selected", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				final int selected = ((Integer) evt.getNewValue()).intValue();
				updateRating(value, selected);
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		return Integer.valueOf(starRatingPanel.getSelected());
	}

	protected abstract int getRating(Object value);

	protected abstract void updateRating(Object value, int rating);

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		this.value = value;

		starRatingPanel.setBackground(table.getSelectionBackground());
		starRatingPanel.setSelected(getRating(value));
		return starRatingPanel;
	}

}

package com.googlecode.vineyard.ui;

import javax.swingx.StarRatingTableCellRenderer;

import com.googlecode.vineyard.model.Wine;

/**
 * 
 * @author Benjamin P. Jung
 */
public class WineRatingTableCellRenderer extends StarRatingTableCellRenderer {

	@Override
	protected int getRating(Object value) {
		if (value instanceof Wine) {
			final Integer taste = ((Wine) value).getRating().getTaste();
			return taste == null ? 0 : taste.intValue();
		}
		return 0;
	}

}

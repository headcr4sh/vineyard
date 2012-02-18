package com.googlecode.vineyard.ui;

import javax.inject.Inject;
import javax.swingx.StarRatingTableCellEditor;

import com.googlecode.vineyard.VineyardApp;
import com.googlecode.vineyard.model.Wine;

/**
 * 
 * @author Benjamin P. Jung
 */
public class WineRatingTableCellEditor extends StarRatingTableCellEditor {

	@Inject VineyardApp app;

	/** @see java.io.Serializable */
	private static final long serialVersionUID = -7715559145848375364L;

	@Override
	protected int getRating(Object value) {
		if (value instanceof Wine) {
			final Integer taste = ((Wine) value).getRating().getTaste();
			return taste == null ? 0 : taste.intValue();
		}
		return 0;
	}

	@Override
	protected void updateRating(Object value, int rating) {
		if (value instanceof Wine) {
			((Wine) value).getRating().setTaste(Integer.valueOf(rating));
		}
	}

}

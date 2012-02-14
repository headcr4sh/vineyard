package javax.swingx;

import javax.inject.Inject;
import javax.inject.Provider;

import com.googlecode.vineyard.ui.IconCache;

/**
 * 
 * @author Benjamin P. Jung
 */
public class StarRatingPanelProvider implements Provider<StarRatingPanel> {

	@Inject
	private IconCache iconCache;

	@Override
	public StarRatingPanel get() {
		final StarRatingPanel panel = new StarRatingPanel(iconCache.getImage("16x16/star-small"), iconCache.getImage("16x16/star-small-empty"));
		return panel;
	}

}

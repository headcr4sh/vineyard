package javax.swingx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingConstants;


/**
 * 
 * @author Benjamin P. Jung
 */
public class StarRatingPanel extends JComponent {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = 1540635974831845619L;

	public static final int DEFAULT_NUM_STARS = 5;
	public static final int DEFAULT_SELECTED = 0;

	public static final int DEFAULT_STAR_WIDTH = 16;
	public static final int DEFAULT_STAR_HEIGHT = 16;

	/** The amount of stars that will be displayed */
	private int numStars;

	/** The amount of stars that will be selected / filled */
	private int selected;

	/** The orientation of this component */
	private int orientation = SwingConstants.HORIZONTAL;

	// Used to draw the graphics of this component
	private Image[] icons = new Image[2];
	private int starWidth;
	private int starHeight;
	
	// c-tor
	StarRatingPanel() {
		this(null, null, DEFAULT_NUM_STARS, DEFAULT_SELECTED);
	}

	// c-tor
	public StarRatingPanel(Image iconSelected, Image iconNotSelected) {
		this(iconSelected, iconNotSelected, DEFAULT_NUM_STARS, DEFAULT_SELECTED);
	}

	// c-tor
	public StarRatingPanel(Image iconSelected, Image iconNotSelected, final int numStars, final int selected) {

		super();

		if (iconSelected != null) {
			setSelectedImage(iconSelected);
		}
		if (iconNotSelected != null) {
			setDeselectedImage(iconNotSelected);
		}
		this.numStars = numStars;
		this.selected = selected;

		setOpaque(false);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final int starIdx = starIndexAt(e.getPoint());
				setSelected(starIdx);
			}
		});

	}

	public void setSelectedImage(Image img) {
		this.icons[0] = img;
		this.starWidth = img.getWidth(this);
		this.starHeight = img.getHeight(this);
		setPreferredSize(new Dimension(numStars * starWidth, starHeight));
	}

	public void setDeselectedImage(Image img) {
		if (starWidth != img.getWidth(this) || starHeight != img.getHeight(this)) {
			throw new IllegalArgumentException("Icons must have exactly the same size.");
		}
		this.icons[1] = img;
	}


	@Override
	protected void paintComponent(final Graphics g) {

		super.paintComponent(g);

		final int width = getWidth();
		final int height = getHeight();

		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0,0, width, height);
		}

		final int starWidth = width / numStars;

		for (int i = 0; i < numStars; i += 1) {
			if (i > 0) {
				if (getOrientation() == SwingConstants.HORIZONTAL) { 
					g.translate(starWidth, 0);
				} else {
					g.translate(0, starHeight);
				}
			}
			final int iconIdx = this.selected > i ? 0 : 1;
			g.setColor(Color.YELLOW);
			g.drawImage(icons[iconIdx], 0, 0, this);
		}

	}

	// ---- HELPER METHODS -----------------------------------------------------

	public int starIndexAt(Point p) {
		switch (getOrientation()) {
		case SwingConstants.HORIZONTAL:
			return p.x / starWidth + 1;
		case SwingConstants.VERTICAL:
			return p.y / starHeight + 1;
		default:
			throw new IllegalStateException();
		}
	}

	public int getStarWidth() {
		return icons[0].getWidth(this);
	}

	public int getStarHeight() {
		return icons[0].getHeight(this);
	}

	// ---- GETTERS AND SETTERS ------------------------------------------------

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		if (selected < 0 || selected > numStars) {
			throw new IllegalArgumentException();
		}
		final int oldSelected = this.selected;
		this.selected = selected;
		firePropertyChange("selected", oldSelected, selected);
		repaint();
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		if (orientation != SwingConstants.HORIZONTAL || orientation != SwingConstants.VERTICAL) {
			throw new IllegalArgumentException("Component orientation can only be set to SwingConstants.HORIZONTAL or SwingConstants.VERTICAL");
		}
		final int oldOrientation = this.orientation;
		this.orientation = orientation;
		firePropertyChange("orientation", oldOrientation, orientation);
	}

}

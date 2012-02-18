package javax.swingx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

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
	public static final Dimension PREFERRED_SIZE = new Dimension(DEFAULT_NUM_STARS * 16, 16);

	/** The amount of stars that will be displayed */
	private int numStars;

	/** The amount of stars that will be selected / filled */
	private int selected;

	/** The orientation of this component */
	private int orientation = SwingConstants.HORIZONTAL;

	// Used to draw the graphics of this component
	private Image[] icons = new Image[2];

	// c-tor
	StarRatingPanel() {
		this(null, DEFAULT_NUM_STARS, DEFAULT_SELECTED);
	}

	public StarRatingPanel(Image selected, Image notSelected) {
		this(new Image[] { selected, notSelected }, DEFAULT_NUM_STARS, DEFAULT_SELECTED);
	}

	// c-tor
	public StarRatingPanel(final Image[] icons, final int numStars, final int selected) {

		super();

		this.icons = icons;
		this.numStars = numStars;
		this.selected = selected;

		setOpaque(false);
		setPreferredSize(PREFERRED_SIZE);

	}

	public void setSelectedImage(Image img) {
		this.icons[0] = img;
	}

	public void setDeselectedImage(Image img) {
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
				g.translate(starWidth, 0);
			}
			final int iconIdx = this.selected > i ? 0 : 1;
			g.setColor(Color.YELLOW);
			g.drawImage(icons[iconIdx], 0, 0, this);
		}

	}

	// ---- GETTERS AND SETTERS ------------------------------------------------

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		// TODO Check constraints
		this.selected = selected;
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

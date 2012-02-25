package com.googlecode.vineyard.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import javax.swingx.StarRatingPanel;

import com.google.inject.Injector;
import com.googlecode.vineyard.model.Country;
import com.googlecode.vineyard.model.Wine;
import com.googlecode.vineyard.model.WineCategory;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author Benjamin P. Jung
 */
public class EditWinePanel extends JPanel {

	/** @see java.io.Serializable */
	private static final long serialVersionUID = 8619523281622971309L;

	public static final int MAX_IMAGE_WIDTH = 176;
	public static final int MAX_IMAGE_HEIGHT = 320;
	public static final float DEFAULT_IMAGE_RATIO = ((float)  MAX_IMAGE_WIDTH) / ((float) MAX_IMAGE_HEIGHT);

	private static final WineCategory DEFAULT_CATEGORY = WineCategory.RED;
	private static final Country DEFAULT_COUNTRY = Country.GERMANY;


	/** IoC injector */
	@Inject private Injector injector;

	@Inject private MainFrame mainFrame;

	/** Underlying model */
	private Wine wine;

	// UI components
	private JLabel imageLabel;
	private JButton setImageButton;
	private JLabel articleIdLabel;
	private JTextField articleIdTextField;
	private JLabel countryLabel;
	private CountryComboBox countryComboBox;
	private JLabel categoryLabel;
	private WineCategoryComboBox categoryComboBox;
	private JLabel productionAreaLabel;
	private JTextField productionAreaTextField;
	private JLabel producerLabel;
	private JTextField producerTextField;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JLabel yearLabel;
	private JTextField yearTextField;
	private JLabel descriptionLabel;
	private JTextArea descriptionText;
	private JLabel tasteRatingLabel;
	@Inject private StarRatingPanel tasteRatingStars;

	public EditWinePanel() {
		super(new MigLayout(
				"insets panel",
				"[]i[trailing]r[leading, grow, fill]u[align trailing]r[align leading, fill]",
				"[baseline]"
			),
			true
		);
		this.wine = new Wine();
	}


	@Inject
	protected void postConstruct() {

		// Construct UI components
		imageLabel = new JLabel();
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.WHITE);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setImageButton = new JButton();
		setImageButton.putClientProperty("JComponent.sizeVariant", "mini");
		setImageButton.putClientProperty("JButton.buttonType", "textured");
		setImageButton.setAction(new LoadImageAction());
		articleIdLabel = new JLabel();
		articleIdTextField = new JTextField();
		countryLabel = new JLabel();
		countryComboBox = injector.getInstance(CountryComboBox.class);
		categoryLabel = new JLabel();
		categoryComboBox = injector.getInstance(WineCategoryComboBox.class);
		productionAreaLabel = new JLabel();
		productionAreaTextField = new JTextField();
		producerLabel = new JLabel();
		producerTextField = new JTextField();
		nameLabel = new JLabel();
		nameTextField = new JTextField();
		yearLabel = new JLabel();
		yearTextField = new JTextField();
		descriptionLabel = new JLabel();
		descriptionText = new JTextArea();
		tasteRatingLabel = new JLabel();

		// Configure UI component appearence
		imageLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		imageLabel.setPreferredSize(new Dimension(MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT));
		imageLabel.setMaximumSize(imageLabel.getPreferredSize());
		articleIdLabel.setText("Article ID:");
		articleIdLabel.setDisplayedMnemonic(KeyEvent.VK_A);
		articleIdLabel.setLabelFor(articleIdTextField);
		countryLabel.setText("Country:");
		countryLabel.setDisplayedMnemonic(KeyEvent.VK_U);
		countryLabel.setLabelFor(countryComboBox);
		countryComboBox.putClientProperty("JComboBox.isPopDown", Boolean.FALSE);
		productionAreaLabel.setText("Area:");
		productionAreaLabel.setDisplayedMnemonic(KeyEvent.VK_R);
		productionAreaLabel.setLabelFor(productionAreaTextField);
		producerLabel.setText("Producer:");
		producerLabel.setDisplayedMnemonic(KeyEvent.VK_P);
		producerLabel.setLabelFor(producerTextField);
		categoryLabel.setText("Category:");
		categoryLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		categoryLabel.setLabelFor(categoryComboBox);
		categoryComboBox.putClientProperty("JComboBox.isPopDown", Boolean.TRUE);
		nameLabel.setText("Name:");
		nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
		nameLabel.setLabelFor(nameTextField);
		yearLabel.setText("Year:");
		yearLabel.setDisplayedMnemonic(KeyEvent.VK_Y);
		yearLabel.setLabelFor(yearTextField);
		yearTextField.setColumns(4);
		descriptionLabel.setText("Description:");
		descriptionLabel.setDisplayedMnemonic(KeyEvent.VK_D);
		descriptionLabel.setLabelFor(descriptionText);
		descriptionText.setColumns(40);
		descriptionText.setRows(8);
		tasteRatingLabel.setText("Taste:");
		tasteRatingLabel.setLabelFor(tasteRatingStars);
		tasteRatingStars.setSelected(0);

		// Place UI components
		add(imageLabel, "spany 5, growy");
		add(articleIdLabel);
		add(articleIdTextField);
		add(countryLabel);
		add(countryComboBox, "span, wrap");
		add(nameLabel);
		add(nameTextField);
		add(categoryLabel);
		add(categoryComboBox, "span, wrap");
		add(productionAreaLabel);
		add(productionAreaTextField);
		add(yearLabel);
		add(yearTextField, "span, wrap");
		add(producerLabel);
		add(producerTextField, "wrap");
		add(new JScrollPane(descriptionText), "skip 2, span, grow, wrap");
		add(setImageButton, "grow");
		add(tasteRatingLabel);
		add(tasteRatingStars);

	}

	public void setWine(final Wine wine) {

		this.wine = wine;

		final BufferedImage wineImage = wine.getImage();
		final Integer wineYear = wine.getYear();
		final WineCategory category = wine.getCategory();
		final Country country = wine.getCountry();

		this.imageLabel.setIcon(wineImage == null ? null : new ImageIcon(wineImage));
		this.articleIdTextField.setText(wine.getArticleId());
		this.countryComboBox.setSelectedItem(country == null ? DEFAULT_COUNTRY : country);
		this.productionAreaTextField.setText(wine.getProductionArea());
		this.producerTextField.setText(wine.getProducer());
		this.nameTextField.setText(wine.getName());
		this.yearTextField.setText(wineYear == null ? "" : String.valueOf(wineYear));
		this.categoryComboBox.setSelectedItem(category == null ? DEFAULT_CATEGORY : category);
		this.descriptionText.setText(wine.getDescription());

	}

	public Wine getWine() {
		return this.wine;
	}

	/**
	 * Updates the underlying wine with the values specified by this panel.
	 */
	public void updateWine() {
		wine.setArticleId(articleIdTextField.getText());
		wine.setName(nameTextField.getText());
		wine.setCountry((Country) countryComboBox.getSelectedItem());
		wine.setProductionArea(productionAreaTextField.getText());
		final String yearStr = yearTextField.getText();
		if (yearStr != null && !yearStr.isEmpty()) {
			wine.setYear(Integer.parseInt(yearStr));
		}
		if (imageLabel.getIcon() != null) {
			final Icon icon = imageLabel.getIcon();
			final BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			icon.paintIcon(this, img.getGraphics(), 0, 0);
			wine.setImage(img);
		} else {
			wine.setImage(null);
		}
		wine.setProducer(producerTextField.getText());
		wine.setCategory((WineCategory) categoryComboBox.getSelectedItem());
		wine.setDescription(descriptionText.getText());
	}


	// ---- INNER CLASSES ------------------------------------------------------

	private final class LoadImageAction extends AbstractAction {

		/** @see java.io.Serializable */
		private static final long serialVersionUID = 6864215390555179268L;

		private LoadImageAction() {
			super();
			putValue(NAME, "Load image...");
			putValue(SHORT_DESCRIPTION, "Loads an image file");
			putValue(MNEMONIC_KEY, KeyEvent.VK_L);
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileFilter() {
				private final String[] SUFFIXES = new String[] {
					".jpg", ".jpeg", ".png", "gif", "tga", "tif", "tiff"
				};
				@Override
				public String getDescription() {
					return "Image files";
				}
				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return true;
					}
					final String fileName = file.getName().toLowerCase();
					for (final String suffix: SUFFIXES) {
						if (fileName.endsWith(suffix)) {
							return true;
						}
					}
					return false;
				}
			});
			final int retVal = fileChooser.showOpenDialog(mainFrame);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				try {

					final BufferedImage origImg = ImageIO.read(fileChooser.getSelectedFile());
					final int imgWidth = origImg.getWidth();
					final int imgHeight = origImg.getHeight();

					float scaleFactorX = 1.0f;
					float scaleFactorY = 1.0f;
					if (imgWidth > MAX_IMAGE_WIDTH) {
						scaleFactorX = ((float) MAX_IMAGE_WIDTH) / ((float) imgWidth);
					}
					if (imgHeight > MAX_IMAGE_HEIGHT) {
						scaleFactorY  = ((float) MAX_IMAGE_HEIGHT) / ((float) imgHeight);
					}
					
					float scaleFactor = 1.0f;
					int scaledImgWidth = imgWidth;
					int scaledImgHeight = imgHeight;
					if (scaleFactorX != 1.0f && scaleFactorY != 1.0f) {
						scaleFactor = scaleFactorX <= scaleFactorY ? scaleFactorX : scaleFactorY;
						scaledImgWidth = (int) (imgWidth * scaleFactor);
						scaledImgHeight = (int) (imgHeight * scaleFactor);
					}

					final Image scaledImg = origImg.getScaledInstance(scaledImgWidth, scaledImgHeight, Image.SCALE_SMOOTH);
					imageLabel.setIcon(new ImageIcon(scaledImg));
				} catch (Exception e) {
					// TODO Error handling!
					e.printStackTrace();
				}
			}
		}

	}

}

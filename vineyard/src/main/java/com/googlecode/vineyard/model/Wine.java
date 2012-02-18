package com.googlecode.vineyard.model;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Benjamin P. Jung
 */
@PersistenceCapable
@XmlRootElement(name = "wine")
public class Wine implements Comparable<Wine> {

	/** Property Changes Support */
	@NotPersistent
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private Long wineId;

	// Properties
	@Persistent private String articleId;
	@Persistent private String name;
	@Persistent private Country country;
	@Persistent private String productionArea;
	@Persistent private Integer year;
	@Persistent private String producer; // TODO: Should be of type "Producer"!
	// TODO Rebsorten / grapes
	@Persistent private WineCategory category; // == color !?
	@Persistent private byte[] imageData;
	@Persistent private String description;
	@Persistent private BigDecimal priceEur;
	@Persistent private Rating rating;

	@NotPersistent private transient BufferedImage image;

	@Override
	public String toString() {
		return String.format("[Wine] %s", getName());
	}

	@Override
	public int compareTo(Wine wine) {
		return this.name.compareTo(wine.name);
	}

	@Override
	public boolean equals(Object o) {

		if (o == null || !(o instanceof Wine)) {
			return false;
		}

		final Wine otherWine = (Wine) o;
		if (wineId != null && otherWine.wineId != null) {
			return wineId.equals(otherWine.wineId);
		} else {
			// TODO Implement!
			return super.equals(o);
		}
	}

	@Override
	public int hashCode() {
		if (this.wineId != null) {
			return this.wineId.hashCode();
		} else {
			// TODO Implement
			return super.hashCode();
		}
	}


	// ---- GETTERS AND SETTERS ------------------------------------------------

	@XmlID
	@XmlAttribute(name = "article-id", required = true)
	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(final String articleId) {
		final String oldArticleId = this.articleId;
		this.articleId = articleId;
		changeSupport.firePropertyChange("articleId", oldArticleId, articleId);
	}

	@XmlElement(name = "name", required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		final String oldName = this.name;
		this.name = name;
		changeSupport.firePropertyChange("name", oldName, name);
	}

	@XmlElement(name = "country", required = true)
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		final Country oldCountry = this.country;
		this.country = country;
		changeSupport.firePropertyChange("country", oldCountry, country);
	}

	@XmlElement(name = "year", required = true)
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		final Integer oldYear = this.year;
		this.year = year;
		changeSupport.firePropertyChange("year", oldYear, year);
	}

	@XmlElement(name = "description", required = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		final String oldDescription = description;
		this.description = description;
		changeSupport.firePropertyChange("description", oldDescription, description);
	}

	@XmlElement(name = "production-area", required = false)
	public String getProductionArea() {
		return this.productionArea;
	}

	public void setProductionArea(final String productionArea) {
		final String oldProductionArea = this.productionArea;
		this.productionArea = productionArea;
		changeSupport.firePropertyChange("productionArea", oldProductionArea, productionArea);
	}

	@XmlElement(name = "producer", required = false)
	public String getProducer() {
		return this.producer;
	}

	public void setProducer(final String producer) {
		final String oldProducer = this.producer;
		this.producer = producer;
		changeSupport.firePropertyChange("producer", oldProducer, producer);
	}

	@XmlElement(name = "category", required = true)
	public WineCategory getCategory() {
		return category;
	}

	public void setCategory(WineCategory category) {
		final WineCategory oldCategory = this.category;
		this.category = category;
		changeSupport.firePropertyChange("category", oldCategory, category);
	}

	@XmlElement(name = "price-eur", required = false)
	public BigDecimal getPriceEur() {
		return priceEur;
	}

	public void setPriceEur(BigDecimal priceEur) {
		final BigDecimal oldPriceEur = this.priceEur;
		this.priceEur = priceEur;
		changeSupport.firePropertyChange("priceEur", oldPriceEur, priceEur);
	}

	public Rating getRating() {
		if (this.rating == null) {
			this.rating = new Rating();
		}
		return rating;
	}

	@XmlTransient
	public BufferedImage getImage() {
		if (this.image == null) {
			if (imageData != null) {
				final byte[] bytes = Arrays.copyOf(imageData, imageData.length);
				final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				try {
					this.image = ImageIO.read(bais);
				} catch (IOException e) {
					// TODO Handle exception!!!
					e.printStackTrace();
				}
			}
		}
		return this.image;
	}

	public void setImage(final BufferedImage image) {
		final BufferedImage oldImage = this.image;
		this.image = image;
		if (this.image == null) {
			imageData = null;
		} else {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(this.image, "png", baos);
				baos.flush();
			} catch (IOException e) {
				// TODO Handle exception!!!
				e.printStackTrace();
			}
			imageData = baos.toByteArray();
		}
		changeSupport.firePropertyChange("image", oldImage, image);
	}


	// ---- PROPERTY CHANGE SUPPORT --------------------------------------------

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

}

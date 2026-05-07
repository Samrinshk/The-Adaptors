package data;

import java.awt.image.BufferedImage;

/**
 * @see ImageProcessor.java
 * 
 * Represents a single image "slice" within a larger sequence or dataset.
 */
public class ImageSlice {
	BufferedImage image;
	int index;
	
	
	/**
	 * Constructor for ImageSlice class
	 * 
	 * @param image - to be stored 
	 * @param index - index represnting the slice's position 
	 */
	public ImageSlice(BufferedImage image, int index) {
		this.image = image;
		this.index = index;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getIndex() {
		return index;
	}

}

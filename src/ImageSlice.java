import java.awt.image.BufferedImage;

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * @see ImageProcessor.java
 */
public class ImageSlice {
	BufferedImage image;
	int index;
	
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

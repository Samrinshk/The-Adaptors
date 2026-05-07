package ui;
import java.awt.image.BufferedImage;
import java.util.List;

import data.ImageSlice;

/**
 * This class generates a combined projection image from CT scan slices.
 * It blends average intensity and maximum intensity so that
 * the heart stays visible while brighter calcifications still stand out.
 */
public class MIPGenerator {

	/**
	 * Generates a combined projection image from the given slices.
	 * 
	 * @param slices the list of CT scan slices
	 * @return the generated image
	 */
	public static BufferedImage generateMIP(List<ImageSlice> slices) {

		if (slices == null || slices.isEmpty()) {
			return null;
		}

		BufferedImage firstslice = slices.get(0).getImage();
		int width = firstslice.getWidth();
		int height = firstslice.getHeight();

		BufferedImage mipImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

		// Use the middle slice range so the heart region is emphasized
		int startSlice = slices.size() / 3;
		int endSlice = (slices.size() * 2) / 3;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				int totalIntensity = 0;
				int maxIntensity = 0;
				int count = 0;

				for (int z = startSlice; z < endSlice; z++) {
					BufferedImage currentslice = slices.get(z).getImage();

					int rgb = currentslice.getRGB(x, y);
					int intensity = rgb & 0xFF;

					totalIntensity += intensity;
					count++;

					if (intensity > maxIntensity) {
						maxIntensity = intensity;
					}
				}

				int avgIntensity = 0;

				if (count > 0) {
					avgIntensity = totalIntensity / count;
				}

				// Blend average and maximum intensity
				int finalIntensity = (avgIntensity * 3 + maxIntensity) / 4;

				int grayRGB = (finalIntensity << 16) | (finalIntensity << 8) | finalIntensity;
				mipImage.setRGB(x, y, grayRGB);
			}
		}

		return mipImage;
	}
}

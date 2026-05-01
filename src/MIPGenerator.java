/**
 * MIP(Maximum Intensity Projection)Generator is responsible for flattenig the 3D 
 * volume into a representative 2D image which will be displayed on the dashboard 
 * 
 * This is done by looping through all the slices in the patiants CT scan, finding the 
 * maximum intensity, in this case calcfication, and painting that onto a 2D image
 * 
 */

import java.awt.image.BufferedImage;
import java.util.List;
/**
 * @param the list of slices for a single patiant
 * @return A 2D BufferedImage representing the MIP
 */

public class MIPGenerator {

	public static BufferedImage generateMIP(List<ImageSlice> slices) {
		
		//chech if patiant has images
		if(slices.isEmpty()) {
			return null;
		}
		
		//get dimensions of the slice
		int width = slices.get(0).getImage().getWidth();
		int height = slices.get(0).getImage().getHeight();
		
		//Creating an image block which will store the final projection
		BufferedImage mipImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//traverse the x , y coordinates
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int maxInsensity = 0;
				
				//look at the z cordinate for the specific x,y coordinate
				for(ImageSlice slice : slices) {
					BufferedImage currentImage = slice.getImage();
					
					//get grb value and grayscale intensity
					int rgb = currentImage.getRGB(x, y);
					int intensity = rgb & 0xFF;
					
					//finding the brightest pixel for this coordinate across the slices
					if(intensity > maxInsensity) {
						maxInsensity = intensity;
					}
				}
				
				//create ab rgb value using the max intensity
				int mipRGB = (maxInsensity << 16)| (maxInsensity << 8) | maxInsensity;
				
				//map the brightest pixel onto the 2D image
				mipImage.setRGB(x, y, mipRGB);
				
			}
		}
		
		return mipImage;
		
	}
	
	
	
	
	
	
}

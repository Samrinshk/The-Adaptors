/**
 * This class will extract the pixel values and categorize them based on calsification intensity
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;


public class GraphMapper {

	/**
	 * we will start by passing the loaded images from the image loader 
	 * into this class
	 */

	private BufferedImage [] loadedimages;
	private int threshold = 200;

	//the constructer that recives the array of loaded images from the imageloader class
	public GraphMapper(BufferedImage[] images) {
		this.loadedimages = images;
	}


	/**
	 * we will implement a triple nested loop to traverse the 3D space
	 */
	public void translateToGraph() {

		//stop the loop here if the loadedimages array is empty
		if(loadedimages == null) {
			return;
		}

		/*
		 * this is the triple nested loop which will traverse the 3D space
		 */
		for (int z = 0; z < loadedimages.length; z++ )//outer loop going through the slices
		{

			BufferedImage currentslice = loadedimages[z];

			int width = currentslice.getWidth();
			int height = currentslice.getHeight();

			for (int y = 0; y < height; y++) {

				for(int x = 0; x < width ; x++) {

					//getting the greyscale value since the images are already in greyscale we can acces the gryscale value dirctly 

					int rgb = currentslice.getRGB(x, y);
					//since the images are already in greyscale we only need to get the last 8 bits which will give us the intensity
					int intensity = rgb & 0xFF;

					//this is where we would call the addNode method but only if the intensity is above the threshhold value
					if(intensity > threshold) {
						RegionNode newnode = new RegionNode(x,y,z, intensity);//creating the new node 
						//Here we will add the node to the graph
						System.out.println(" calcification at " + x+ ", " + y + ", " + z);
					}

				}
			}

		}

	}


}

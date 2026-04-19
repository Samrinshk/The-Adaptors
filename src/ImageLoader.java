/**
 *this class is the class which will load the images into java
 *due to the fact that there are so many images that need to be loaded we will load
 *the images by the folder instead if one by one
 */


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class ImageLoader {

	private BufferedImage [] loadedimages;

	public static void main(String [] args) {

		/*
		 * loading the images from a specific folder
		 * when loadin from the peter doe folder write 
		 * "Images/Patient-Doe Peter/Patient-Doe Peter/Study-2-CT[20010101]/Series-007"
		 */
		
		ImageLoader load = new ImageLoader("Images/Patient-Doe Peter/Patient-Doe Peter/Study-2-CT[20010101]/Series-007");
		
		

	}

	/**
	 *This class contains the logic for loading the images
	 */
	public ImageLoader(String path) {

		File folder = new File(path);

		//Checking if the folder exist before performing any operations
		if(!folder.exists() || !folder.isDirectory()) {
			System.err.println(" Directry path does not exsist" + path);
			return;
		}

		//putting all the images in the folder into an array that we can iterate through
		File[] imageFiles = folder.listFiles();

		//we first check if the array is actually populated
		if(imageFiles != null) {

			//creating a new BufferedImage array where the loaded images will be stored
			loadedimages = new BufferedImage[imageFiles.length];

			/*
			 * the for loop iterates through all the images in the array and reads them
			 */
			for (int i = 0; i < imageFiles.length; i++) {

				try {
					loadedimages[i] = ImageIO.read(imageFiles[i]);
					System.out.println("loaded " + imageFiles[i].getName());


				} catch (IOException e) {
					System.err.println("error reading file");
					e.printStackTrace();
				}

			}
		}

		/*
		 * here we are passing the array into a graph mapper instance
		 */
		 if(loadedimages != null) {
			 System.out.println("Loading complete. Starting graph mapping...");
			 GraphMapper mapper = new GraphMapper(loadedimages);
			 mapper.translateToGraph();
			 
		 }
		
		
		
	}
	
	
	
	

}

/**
 *this class is the class which will load the images into java
 *due to the fact that there are so many images that need to be loaded we will load
 *the images by the folder instead if one by one
 */
package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *This class contains the logic for loading the images
 */
public class ImageLoader {

	//stores all succesfully loaded image slices
	private BufferedImage [] loadedimages;

	/**
	 * Constructor for the ImageLoader class 
	 * @param path - file path 
	 */
	public ImageLoader(String path) {

		File folder = new File(path);

		//Checking if the folder exist before performing any operations
		if(!folder.exists() || !folder.isDirectory()) {
			System.err.println(" Directry path does not exsist" + path);
			loadedimages = new BufferedImage[0];
			return;
		}

		//putting all the images in the folder into an array that we can iterate through
		File[] imageFiles = folder.listFiles();
		
		
		//we first check if the array is actually populated
		  if(imageFiles != null) { 
			  for(int i = 0; i < imageFiles.length; i++) {
				  for(int j = i + 1; j < imageFiles.length; j++) {
					  if(imageFiles[i].getName().compareToIgnoreCase(imageFiles[j].getName()) > 0) {
						  File temp = imageFiles[i];
						  imageFiles[i] = imageFiles[j];
						  imageFiles[j] = temp;
					  }
				  }
			  }
		
			loadedimages = new BufferedImage[imageFiles.length];

			/*
			 * the for loop iterates through all the images in the array and reads them
			 */
			for (int i = 0; i < imageFiles.length; i++) {

				try {
					if(imageFiles[i].isFile()) {
						loadedimages[i] = ImageIO.read(imageFiles[i]);
						
						if(loadedimages[i] != null) {
							System.out.println("loaded " + imageFiles[i].getName());
							//System.out.println("Loaded " + loadedimages.length + " slices");
						}else {
							System.out.println("Skipped, non-image file: " + imageFiles[i].getName());
						}
					}
					
				} catch (IOException e) {
					System.err.println("error reading file" + imageFiles[i].getName());
					e.printStackTrace();
				}

			}
		}else {
			loadedimages = new BufferedImage[0];
		}	
	}
	
	/**
	 * Returns the loaded images that will be passed to the GraphMapper
	 */
	 public BufferedImage[] getLoadedImages() {
		 return loadedimages;
	 }
	 /**
	  * checks if any images were loaded
	  */
	 public boolean hasImage() {
		 return loadedimages !=  null && loadedimages.length >0;
	 }
	
}

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * Handles loading images via ImageIO
 * Since we are handeling 3D CT scans we will be loading the entire folder of 
 * images into memory all at once using an array list
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * loads all slices for each patient
 * sort them
 * and stores them in memory
 */
public class ImageProcessor 
{
	public List<ImageSlice> loadSlices(String filePath){
		List<ImageSlice> slices = new ArrayList<>();
		
		File folder = new File(filePath);
		File[] files = folder.listFiles();
		
		for(File file : files) {
			String fileName = file.getName().toLowerCase();
			if(fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
				try {
					BufferedImage image = ImageIO.read(file);
					int index = getIndex(file.getName());							//get index of the image(image number)
					
					ImageSlice newImgSlice = new ImageSlice(image, index);
					slices.add(newImgSlice);										//add each slice of the heart to list
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		//sort slices in ascending order incase they are loaded out of order
		Collections.sort(slices, Comparator.comparingInt(s -> s.index));			//orders the slices according to their indices
		return slices;
	}
	
	/**
	 * helper method, replace all characters except digits
	 * @param fileName
	 */
	public int getIndex(String fileName) {
		String number = fileName.replaceAll("\\D+", "");							//regex
		int index = Integer.parseInt(number);
		
		return index;
	}
	
	//sorting images according to patient
	/**
	 * Stores the slices according to or under each patient
	 * @param dataPath
	 * @return
	 */
	public List<Patient> loadDataset(String dataPath){
		List<Patient> patients = new ArrayList<>();
		
		File fileRoot = new File(dataPath);
		
		for(File patientFolder : fileRoot.listFiles()) {
			if(patientFolder.isDirectory()) {
				List<ImageSlice> imgSlices = loadSlices(patientFolder.getPath());
				
				Patient newPatient = new Patient(patientFolder.getName(), imgSlices);
				patients.add(newPatient);
			}
		}
		
		return patients;
	}
}































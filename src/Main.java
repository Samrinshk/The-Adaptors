import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * The starting point of the application that launches JavaFX.
 */

public class Main 
{
	

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String datasetPath = "Images";
		
		PatientDatasetLoader datasetLoader = new PatientDatasetLoader();
		
		List<Patient> patients = datasetLoader.loadPatients(datasetPath);
		
		System.out.println("Total patients loaded: " + patients.size());
		
		for(Patient patient : patients) {
			//testiiiiiing
			System.out.println("----------------------------------");
			System.out.println("Graph created YAY!!");
			System.out.println("Patient ID: " + patient.getId());
			System.out.println("Num slices: " + patient.getImgSlices().size());
			System.out.println("Num nodes: " + patient.getRag().getNodeCount());
			System.out.println("Num edges: " + patient.getRag().getEdgeCount());
			System.out.println("Extracted graph features: " + patient.getFeatures());
			
		}
		
	}

}

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
		
		//Testing SimilarityDetector
		
		if (!patients.isEmpty())
		{
			System.out.println("----------------------------------");
			System.out.println("--Running Similarity Search--");
			SimilarityDetector detector = new SimilarityDetector();
			
			Patient targetPatient = patients.get(0);
			
			int k = 3;
			List<Patient> matches = detector.findTopMatch(targetPatient.getFeatures(), patients, k);
			
			System.out.println("Top " + k + " matches for Patient " + targetPatient.getId() + ":");
			
			for (Patient match : matches)
			{
				double distance = detector.compare(targetPatient.getFeatures(), match.getFeatures());
				System.out.println(" > Patient ID: " + match.getId() + " (Distance: " + String.format("%4f", distance) + ")");
			}
		}
		
		//Testing KD-Tree
		
		//Initialise KD-Tree
		KDTree patientTree = new KDTree();
		
		for (Patient p : patients)
		{
			GraphFeatures f = p.getFeatures();
			
			//create feature vector 
			double[] featureVector = {
					f.getAvgIntensity(),
					f.getDensity(),
					f.getAvgDegree()
			};
			
			//Insert into KDTree
			patientTree.insert(featureVector, p);
		}
		
		System.out.println("KD-Tree populated with " + patients.size() + " patients.");
		
		if (!patients.isEmpty()) {
			//pick a target patient - first
			Patient targetP = patients.get(0);
			
			double[] targetVector = {
					targetP.getFeatures().getAvgIntensity(),
					targetP.getFeatures().getDensity(),
					targetP.getFeatures().getAvgDegree()
			};
			
			System.out.println("Searching KD-Tree for top 3 matches for: " + targetP.getId()); 
			
			//Invoke the findKNearest method
			List<Patient> matches = patientTree.findKNearest(targetVector, 3);
			
			for (int i = 0 ; i < matches.size(); i++)
			{
				Patient match = matches.get(i);
				
				double dist = patientTree.distance(targetVector, new double[] {
						match.getFeatures().getAvgIntensity(),
						match.getFeatures().getDensity(),
						match.getFeatures().getAvgDegree()
				});
				
				System.out.println((i+1) + ". Match: " + match.getId() + " | Distance: " + String.format("%4f", dist));
			}
			
		}
	}

}

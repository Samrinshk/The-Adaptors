import java.util.List;

/**
 * Main workflow:
 * 1. Load known disease cases
 * 2. Build graphs and features for known cases
 * 3. Store known cases in KDTree
 * 4. Load unknown patients
 * 5. Compare unknown patients to known disease cases
 * 6. Classify each patient into the category of the closest known case
 */
public class Main {

	public static void main(String[] args) {

		String knownDatasetPath = "Images\\Known Des";
		String patientDatasetPath = "Images\\Patients";

		PatientDatasetLoader datasetLoader = new PatientDatasetLoader();

		// Load known disease cases
		List<Patient> knownPatients = datasetLoader.loadKnownPatients(knownDatasetPath);
		System.out.println("Total known patients loaded: " + knownPatients.size());

		// Load unknown patients
		List<Patient> patients = datasetLoader.loadPatients(patientDatasetPath);
		System.out.println("Total unknown patients loaded: " + patients.size());

		// Print summaries for known disease cases
		for (Patient patient : knownPatients) {
			System.out.println("----------------------------------");
			System.out.println("Known Case Loaded");
			System.out.println("Patient ID: " + patient.getId());
			System.out.println("Category: " + patient.getCategory());
			System.out.println("Num slices: " + patient.getImgSlices().size());
			System.out.println("Num nodes: " + patient.getRag().getNodeCount());
			System.out.println("Num edges: " + patient.getRag().getEdgeCount());
			System.out.println("Extracted graph features: " + patient.getFeatures());
		}

		// Build KDTree from known disease cases only
		KDTree patientTree = new KDTree();

		for (Patient p : knownPatients) {
			GraphFeatures f = p.getFeatures();

			double[] featureVector = {
					f.getAvgIntensity(),
					f.getDensity(),
					f.getAvgDegree()
			};

			patientTree.insert(featureVector, p);
		}

		System.out.println("KD-Tree populated with " + knownPatients.size() + " known cases.");

		// Create classifier
		KNNClassifier knn = new KNNClassifier();

		// Compare and classify each unknown patient
		for (Patient targetP : patients) {

			System.out.println("----------------------------------");
			System.out.println("Unknown Patient Loaded");
			System.out.println("Patient ID: " + targetP.getId());
			System.out.println("Num slices: " + targetP.getImgSlices().size());
			System.out.println("Num nodes: " + targetP.getRag().getNodeCount());
			System.out.println("Num edges: " + targetP.getRag().getEdgeCount());
			System.out.println("Extracted graph features: " + targetP.getFeatures());

			// Show top 3 most similar known disease cases
			List<Patient> matches = knn.getNearestNeighbours(targetP, patientTree, 3);

			System.out.println("Most similar known disease cases:");
			for (int i = 0; i < matches.size(); i++) {
				Patient match = matches.get(i);

				double[] targetArr = {
						targetP.getFeatures().getAvgIntensity(),
						targetP.getFeatures().getDensity(),
						targetP.getFeatures().getAvgDegree()
				};

				double[] matchArr = {
						match.getFeatures().getAvgIntensity(),
						match.getFeatures().getDensity(),
						match.getFeatures().getAvgDegree()
				};

				double dist = patientTree.distance(targetArr, matchArr);

				System.out.println((i + 1) + ". " + match.getId()
						+ " | Category: " + match.getCategory()
						+ " | Distance: " + String.format("%.4f", dist));
			}

			// Final classification uses only the single closest known disease case
			String predictedCategory = knn.Classify(targetP, patientTree);
			targetP.setCategory(predictedCategory);

			System.out.println("Predicted Category: " + targetP.getCategory());
		}
	}
}

import java.util.List;

/**
 * This class classifies an unknown patient by finding
 * the single most similar known disease case.
 */
public class KNNClassifier {

	/**
	 * Returns the nearest known disease cases for the target patient.
	 * 
	 * @param targetPatient the unknown patient being compared
	 * @param tree the KDTree containing known disease cases
	 * @param k the number of similar cases to return
	 * @return list of nearest known cases
	 */
	public List<Patient> getNearestNeighbours(Patient targetPatient, KDTree tree, int k) {
		GraphFeatures target = targetPatient.getFeatures();

		double[] targetPoint = {
				target.getAvgIntensity(),
				target.getDensity(),
				target.getAvgDegree()
		};

		return tree.findKNearest(targetPoint, targetPatient, k);
	}

	/**
	 * Classifies the unknown patient using the single closest known disease case.
	 * 
	 * @param targetPatient the unknown patient
	 * @param tree the KDTree containing known disease cases
	 * @return the predicted disease category
	 */
	public String Classify(Patient targetPatient, KDTree tree) {
		List<Patient> neighbours = getNearestNeighbours(targetPatient, tree, 1);

		if (neighbours.isEmpty()) {
			return "Unknown";
		}

		// Use the category of the single closest known case
		Patient bestMatch = neighbours.get(0);
		return bestMatch.getCategory();
	}
}

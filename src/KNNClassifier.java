import java.util.List;

public class KNNClassifier {
	
	public List<Patient> getNearestNeighbours (Patient targetPatient, KDTree tree, int k) {
		
		GraphFeatures target = targetPatient.getFeatures();
		
		//Covert feature object into the double array the KDTree needs
		double[] targetPoint = {
				target.getAvgIntensity(),
				target.getDensity(),
				target.getAvgDegree()
		};
		
		return tree.findKNearest(targetPoint, targetPatient, k);
	}
	
	public String Classify(Patient targetPatient, KDTree tree, int k) {
		
		List<Patient> neighbours = getNearestNeighbours(targetPatient, tree, k);
		
		if (neighbours.isEmpty())
		{
			return "No similar cases found";
		}
		
		//Classify the current scan 
		Patient BestMatch = neighbours.get(0);
		
		return "Scan classified as similar to Case: " + BestMatch.getId() + 
				" (Based on " + neighbours.size() + " nearest neighbours)";		
	}
	
}

import java.util.List;

public class KNNClassifier {
	
	public List<Patient> getNearestNeighbours (GraphFeatures target, KDTree tree, int k) {
		
		//Covert feature object into the double array the KDTree needs
		double[] targetPoint = {
				target.getAvgIntensity(),
				target.getDensity(),
				target.getAvgDegree()
		};
		
		return tree.findKNearest(targetPoint, k);
	}
	
	public String Classify(GraphFeatures target, KDTree tree, int k) {
		
		List<Patient> neighbours = getNearestNeighbours(target, tree, k);
		
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

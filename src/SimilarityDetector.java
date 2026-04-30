import java.util.Comparator;
import java.util.List;

public class SimilarityDetector {
	
	/**
	 * Compares 2 functions and returns the distance
	 * (lower value = more similar)
	 */
	public double compare(GraphFeatures a, GraphFeatures b )
	{
		//Calculate the difference between each feature 
		double diffIntensity = a.getAvgIntensity() - b.getAvgIntensity();
		double diffDensity = a.getDensity() - b.getDensity();
		double diffDegree = a.getAvgDegree() - b.getAvgDegree();
		
		return Math.sqrt(
				Math.pow(diffIntensity, 2) +
				Math.pow(diffDensity, 2)+
				Math.pow(diffDegree, 2));
	}
	
	/**
	 * Finds the top 'k' most similar patients to a target patient
	 */
	public List<Patient> findTopMatch(GraphFeatures target, List<Patient> dataset, int k)
	{
		dataset.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient p1, Patient p2) {
				double dist1 = SimilarityDetector.this.compare(target, p1.getFeatures());
				double dist2 = SimilarityDetector.this.compare(target, p2.getFeatures());
				return Double.compare(dist1, dist2);
			}
			
		});
		
		
		return dataset.subList(0, Math.min(k,  dataset.size()));
		
	}
}

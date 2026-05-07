package logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data.Patient;

/**
 * Uses methods to determine similarity between patient datasets
 */
public class SimilarityDetector {
	
	/**
	 * Compares 2 functions 
	 * (lower value = more similar)
	 * 
	 * @param a - first graph feature 
	 * @param b - second graph feature
	 * @return the distance
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
	 * 
	 * @param target - patient used as the reference
	 * @param dataset - list of patients
	 * @param k - number of matches to return 
	 * @return - list of the most similar patient objects sorted by distance
	 */
	public List<Patient> findTopMatch(Patient target, List<Patient> dataset, int k)
	{
		// Create a copy so the original dataset order is not changed
		List<Patient> filtered  = new ArrayList<>();
		
		// Exclude the target patient from the comparison list
		for(int i = 0; i < dataset.size(); i++){
			Patient patient = dataset.get(i);
			if(!patient.getId().equals(target.getId())) {
				filtered.add(patient);
			}
		}
		
		// Sort the copied list by similarity
		filtered.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient p1, Patient p2) {
				double dist1 = SimilarityDetector.this.compare(target.getFeatures(), p1.getFeatures());
				double dist2 = SimilarityDetector.this.compare(target.getFeatures(), p2.getFeatures());
				return Double.compare(dist1, dist2);
			}
			
		});
		
		
		return filtered.subList(0, Math.min(k,  filtered.size()));
		
	}
}

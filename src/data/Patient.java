package data;

import java.util.List;
import logic.GraphFeatures;
import structures.RAG;

/**
 * Represents a patient profile
 */
public class Patient {
	
	private String id;
	private List<ImageSlice> imgSlices; 
	private RAG rag;
	private GraphFeatures features;
	private String category;
	
	/**
	 * Constructir for Patient class 
	 * Creates a patient with a known category
	 * 
	 * @param id - patient id 
	 * @param category - classification
	 * @param imgSlices - list of slices of one ct scan 
	 */
	public Patient(String id, String category, List<ImageSlice> imgSlices) {
		this.id = id;
		this.imgSlices = imgSlices;
		this.category = category;
	}
	
	
	/**
	 * Constructor for unknown patients.
	 * 
	 * @param id - patient id 
	 * @param imgSlices - list of slices of one ct scan 
	 */
	public Patient(String id, List<ImageSlice> imgSlices) {
		this.id = id;
		this.category = "Unknown";
		this.imgSlices = imgSlices;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ImageSlice> getImgSlices() {
		return imgSlices;
	}

	public void setImgSlices(List<ImageSlice> imgSlices) {
		this.imgSlices = imgSlices;
	}

	public RAG getRag() {
		return rag;
	}

	public void setRag(RAG rag) {
		this.rag = rag;
	}

	public GraphFeatures getFeatures() {
		return features;
	}

	public void setFeatures(GraphFeatures features) {
		this.features = features;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Determines if the patient has a valid, known category
	 * 
	 * @return - category or unknown
	 */
	public boolean hasKnownCategory() {
		return category != null
				&& !category.trim().isEmpty()
				&& !category.equalsIgnoreCase("Unknown");
	}
	
}

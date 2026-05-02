import java.util.List;

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * Patient class
 */
public class Patient {
	
	private String id;
	private List<ImageSlice> imgSlices; 
	private RAG rag;
	private GraphFeatures features;
	private String category;
	
	
	
	public Patient(String id, String category, List<ImageSlice> imgSlices) {
		this.id = id;
		this.imgSlices = imgSlices;
		this.category = category;
	}
	
	/**
	 * Constructor for unknown patients.
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
	
	public boolean hasKnownCategory() {
		return category != null
				&& !category.trim().isEmpty()
				&& !category.equalsIgnoreCase("Unknown");
	}
	
}

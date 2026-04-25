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
	
	//patient id: unique identifier for the patient
	private String id;
	// the list of the ct scan slices that belong to the patient 
	private List<ImageSlice> imgSlices;
	//this will be the RAG build from the scans 
	private RAG rag;
	// extracts feature summary of the graph
	private GraphFeatures features;
	
	
	
	public Patient(String id, List<ImageSlice> imgSlices) {
		this.id = id;
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
	
}

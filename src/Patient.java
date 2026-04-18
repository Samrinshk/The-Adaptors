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
	String id;
	List<ImageSlice> imgSlices;
	
	public Patient(String id, List<ImageSlice> imgSlices) {
		this.id = id;
		this.imgSlices = imgSlices;
	}
}

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * The starting point of the application that launches JavaFX.
 */

public class Main 
{
	

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		/*
		 * loading the images from a specific folder
		 * when loadin from the peter doe folder write 
		 * "Images/Patient-Doe Peter/Patient-Doe Peter/Study-2-CT[20010101]/Series-007"
		 */
		String path = "Images/Patient-Doe Peter/Patient-Doe Peter/Study-2-CT[20010101]/Series-007";
		
		//loads the images slices from the folder
		ImageLoader ILoader = new ImageLoader(path);
		
		//if not images are loade this will make the program stop
		if(!ILoader.hasImage()) {
			System.out.println("No images were loaded.");
			return;
		}
		
		//get loaded images images
		BufferedImage[] loadedimages = ILoader.getLoadedImages();
		
		//Convert loaded imeges intp ImageSlice objects
		List<ImageSlice> slices = new ArrayList<>();
		for(int i =0; i < loadedimages.length; i++) {
			if(loadedimages[i] != null) {
				slices.add(new ImageSlice(loadedimages[i],i));
			}
		}
		
		// we create a patient object for the scan
		Patient patient = new Patient("Patient-01", slices);
		
		//pass loaded images into graph mapper
		GraphMapper mapper = new GraphMapper(loadedimages);
		
		// convert loaded slices into RAG
		RAG rag = mapper.translateToGraph();
		
		// stor graph inside patient
		patient.setRag(rag);
		
		//testiiiiiing
		System.out.println("Graph created YAY!!");
		System.out.println("Patient ID: " + patient.getId());
		System.out.println("Num slices: " + patient.getImgSlices().size());
		System.out.println("Num nodes: " + patient.getRag().getNodeCount());
		System.out.println("Num edges: " + patient.getRag().getEdgeCount());
		
		
		
		
		
		
		//TESTING TESTING TESTING TESTNG
//		ImageProcessor loader = new ImageProcessor();
//		
//		List<Patient> patients = loader.loadDataset("/C://Users//user//Desktop//The-Adaptors//Images/");
//		
//		System.out.println("Total patients: " + patients.size());
//		
//		for(Patient p : patients) {
//			System.out.println("Patient: " + p.id + " || slices: " + p.imgSlices.size());
//		}
		
//		//testing order of images (2)
//		for(Patient p : patients) {
//			System.out.println("Patient: " + p.id);
//			for(int i = 0; i < Math.min(5,  p.imgSlices.size()); i++) {
//				System.out.println("Slices no.: " + p.imgSlices.get(i).index);
//			}
//		}
		
		//testing (3)
//		for(Patient p : patients) {
//			List<ImageSlice> slice = p.imgSlices;
//			System.out.println(p.id + " || firstSlice: " + slice.get(0).index + " || last: " + slice.get(slice.size()-1).index);
//		}
	}

}

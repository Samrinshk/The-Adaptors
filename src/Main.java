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

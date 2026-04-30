import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PatientDatasetLoader {
 
	public List<Patient> loadPatients(String datasetPath){
		List<Patient> patients = new ArrayList<>();
		
		File datasetFolder = new File(datasetPath);
		
		if(!datasetFolder.exists()|| !datasetFolder.isDirectory()) {
			System.out.println("Dataset foulder is not found: " + datasetPath);
			return patients;
		}
		
		File[] patientFolders = datasetFolder.listFiles();
		
		if(patientFolders == null) {
			return patients;
		}
		
		for(File patientFolder : patientFolders) {
			if(patientFolder.isDirectory()) {
				String patientID = patientFolder.getName();
				File imageFolder = findImageFolder(patientFolder);
				
				if(imageFolder == null) {
					System.out.println("No image folder found for patient: " + patientID);
					continue;
				}
				
				ImageLoader ILoader = new ImageLoader(imageFolder.getPath());
				
				if(!ILoader.hasImage()) {
					System.out.println("No images loaded for patient: " + patientID);
					continue;
				}
				BufferedImage[] loadedImages = ILoader.getLoadedImages();
				
				List<ImageSlice> slices = new ArrayList<>();
				for(int i =0; i < loadedImages.length; i++) {
					if(loadedImages[i] != null) {
						slices.add(new ImageSlice(loadedImages[i],i));
					}
				}
				
				// we create a patient object for the scan
				Patient patient = new Patient(patientID, slices);
				
				//pass loaded images into graph mapper
				GraphMapper mapper = new GraphMapper(loadedImages);
				
				// convert loaded slices into RAG
				RAG rag = mapper.translateToGraph();
				
				// store graph inside patient
				patient.setRag(rag);
				
				//Extract graph features from graph
				FeatureExtractor FExtractor = new FeatureExtractor();
				GraphFeatures GFeatures = FExtractor.extractFeatures(rag);
				
				//store the extracted features inside the patient
				patient.setFeatures(GFeatures);
				
				//Add the completed patient object to the list
				patients.add(patient);
				
				System.out.println("Finished Processinf patient: " + patientID);
			}
		}
		
		return patients;
	}

	/**
	 * find the folder thta cntains the image files 
	 * if the given folder already contains the images it returns that folder
	 * otherwise, it searchers recurlivley through te subfolders.
	 * 
	 * @param folder
	 * @return
	 */
	private File findImageFolder(File folder) {
		File[] files = folder.listFiles();
		
		if(files == null) {
			return null;
		}
		
		boolean hasImages = false;
		
		for(File file: files) {
			if(file.isFile() && isImageFile(file.getName())) {
				hasImages = true; 
				break;
			}
		}
		
		if(hasImages) {
			return folder;
		}
		
		for(File file : files) {
			if(file.isDirectory()) {
				File found = findImageFolder(file);
				if(found != null) {
					return found;
				}
			}
		}
		return null;
	}

	private boolean isImageFile(String fileName) {
		String lower = fileName.toLowerCase();
		
		return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png") || lower.endsWith(".bmp");
	}
}

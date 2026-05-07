package data;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import logic.FeatureExtractor;
import logic.GraphFeatures;
import logic.GraphMapper;
import structures.RAG;

public class PatientDatasetLoader {

	/**
	 * Loads unknown patients from Images/Patients.
	 */
	public List<Patient> loadPatients(String datasetPath) {
		List<Patient> patients = new ArrayList<>();

		File datasetFolder = new File(datasetPath);

		if (!datasetFolder.exists() || !datasetFolder.isDirectory()) {
			System.out.println("Dataset folder is not found: " + datasetPath);
			return patients;
		}

		File[] patientFolders = datasetFolder.listFiles();

		if (patientFolders == null) {
			return patients;
		}

		for (File patientFolder : patientFolders) {
			if (patientFolder.isDirectory()) {
				String patientID = patientFolder.getName();
				File imageFolder = findImageFolder(patientFolder);

				if (imageFolder == null) {
					System.out.println("No image folder found for patient: " + patientID);
					continue;
				}

				ImageLoader ILoader = new ImageLoader(imageFolder.getPath());

				if (!ILoader.hasImage()) {
					System.out.println("No images loaded for patient: " + patientID);
					continue;
				}

				BufferedImage[] loadedImages = ILoader.getLoadedImages();

				List<ImageSlice> slices = new ArrayList<>();
				for (int i = 0; i < loadedImages.length; i++) {
					if (loadedImages[i] != null) {
						slices.add(new ImageSlice(loadedImages[i], i));
					}
				}

				Patient patient = new Patient(patientID, slices);

				GraphMapper mapper = new GraphMapper(loadedImages);
				RAG rag = mapper.translateToGraph();
				patient.setRag(rag);

				FeatureExtractor FExtractor = new FeatureExtractor();
				GraphFeatures GFeatures = FExtractor.extractFeatures(rag);
				patient.setFeatures(GFeatures);

				patients.add(patient);

				System.out.println("Finished processing patient: " + patientID);
			}
		}

		return patients;
	}

	/**
	 * Loads known patients from Images/Known Des.
	 * Folder format:
	 * patientID - category
	 */
	public List<Patient> loadKnownPatients(String datasetPath) {
		List<Patient> patients = new ArrayList<>();

		File datasetFolder = new File(datasetPath);

		if (!datasetFolder.exists() || !datasetFolder.isDirectory()) {
			System.out.println("Known dataset folder is not found: " + datasetPath);
			return patients;
		}

		File[] patientFolders = datasetFolder.listFiles();

		if (patientFolders == null) {
			return patients;
		}

		for (File patientFolder : patientFolders) {
			if (patientFolder.isDirectory()) {
				String folderName = patientFolder.getName();

				String patientID = folderName;
				String category = "Unknown";

				String[] parts = folderName.split(" - ", 2);
				if (parts.length == 2) {
					patientID = parts[0].trim();
					category = parts[1].trim();
				}

				File imageFolder = findImageFolder(patientFolder);

				if (imageFolder == null) {
					System.out.println("No image folder found for known patient: " + patientID);
					continue;
				}

				ImageLoader ILoader = new ImageLoader(imageFolder.getPath());

				if (!ILoader.hasImage()) {
					System.out.println("No images loaded for known patient: " + patientID);
					continue;
				}

				BufferedImage[] loadedImages = ILoader.getLoadedImages();

				List<ImageSlice> slices = new ArrayList<>();
				for (int i = 0; i < loadedImages.length; i++) {
					if (loadedImages[i] != null) {
						slices.add(new ImageSlice(loadedImages[i], i));
					}
				}

				Patient patient = new Patient(patientID, category, slices);

				GraphMapper mapper = new GraphMapper(loadedImages);
				RAG rag = mapper.translateToGraph();
				patient.setRag(rag);

				FeatureExtractor FExtractor = new FeatureExtractor();
				GraphFeatures GFeatures = FExtractor.extractFeatures(rag);
				patient.setFeatures(GFeatures);

				patients.add(patient);

				System.out.println("Finished processing known patient: " + patientID + " | Category: " + category);
			}
		}

		return patients;
	}

	private File findImageFolder(File folder) {
		File[] files = folder.listFiles();

		if (files == null) {
			return null;
		}

		boolean hasImages = false;

		for (File file : files) {
			if (file.isFile() && isImageFile(file.getName())) {
				hasImages = true;
				break;
			}
		}

		if (hasImages) {
			return folder;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				File found = findImageFolder(file);
				if (found != null) {
					return found;
				}
			}
		}

		return null;
	}

	private boolean isImageFile(String fileName) {
		String lower = fileName.toLowerCase();

		return lower.endsWith(".jpg")
				|| lower.endsWith(".jpeg")
				|| lower.endsWith(".png")
				|| lower.endsWith(".bmp");
	}
}

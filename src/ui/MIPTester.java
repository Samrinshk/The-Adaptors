/*
 * this is just to test if the MIPGenerator works, it will not be in
 * the final draft
 */
package ui;

import javax.imageio.ImageIO;

import data.Patient;
import data.PatientDatasetLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MIPTester {

	public static void main(String[] args) {

		System.out.println("--- Starting MIP Generator Test ---");

		// 1. Load the dataset just like you do in Main.java
		String datasetPath = "Images";
		PatientDatasetLoader datasetLoader = new PatientDatasetLoader();
		List<Patient> patients = datasetLoader.loadPatients(datasetPath);

		// 2. Safety check
		if (patients == null || patients.isEmpty()) {
			System.out.println("Test Failed: No patients were loaded from the '" + datasetPath + "' folder.");
			return;
		}

		// 3. Grab the first patient to test
		Patient targetPatient = patients.get(0);
		System.out.println("Successfully loaded Patient: " + targetPatient.getId());
		System.out.println("Total slices found: " + targetPatient.getImgSlices().size());

		System.out.println("Generating Maximum Intensity Projection (MIP)... this might take a second.");

		// 4. Run your new MIP Generator
		BufferedImage mipImage = MIPGenerator.generateMIP(targetPatient.getImgSlices());

		if (mipImage != null) {
			try {
				// 5. Save the generated image to your computer so you can actually see it!
				File outputFile = new File("MIP_Test_Output_" + targetPatient.getId() + ".png");
				ImageIO.write(mipImage, "png", outputFile);

				System.out.println("SUCCESS! MIP image generated and saved to: " + outputFile.getAbsolutePath());
				System.out.println("Go check your project folder to see the flattened image!");

			} catch (IOException e) {
				System.out.println("Error saving the image: " + e.getMessage());
			}
		} else {
			System.out.println("Test Failed: MIPGenerator returned a null image.");
		}

		System.out.println("--- Test Complete ---");
	}
}

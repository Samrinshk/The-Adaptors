package ui;
import javax.swing.*;

import data.Patient;
import data.PatientDatasetLoader;
import logic.GraphFeatures;
import logic.KNNClassifier;
import structures.KDTree;

import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Dashboard extends JFrame {

	
	private List<Patient> loadedPatients;
	private Patient currentPatient;

	
	private JButton btnLoadPatient;
	private JButton btnStartClass;
	private JComboBox<String> patientDropdown;
	private JProgressBar progressBar;
	private JList<String> knownDatasetList;

	
	private JLabel imageLabel;

	private JLabel lblValIntensity;
	private JLabel lblValDisease;
	private JLabel lblValRisk;

	
	private JLabel lblStatus;
	private JLabel lblLoadedPatient;

	public Dashboard() {
		setTitle("The Adaptors: CT Calcification Analyzer");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));
		getContentPane().setBackground(new Color(245, 248, 250)); // Light bluish-gray background

		
		loadedPatients = new ArrayList<>();

		buildLeftPanel();
		buildCenterPanel();
		buildRightPanel();
		buildBottomBar();

		
		setupActionListeners();
		setupDragAndDrop();
	}

	private void buildLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setPreferredSize(new Dimension(250, 0));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		btnLoadPatient = new JButton("Load Patient Data");
		btnLoadPatient.setBackground(new Color(32, 163, 168)); 
		btnLoadPatient.setForeground(Color.BLACK); 
		btnLoadPatient.setFont(new Font("SansSerif", Font.BOLD, 14)); 
		btnLoadPatient.setFocusPainted(false);
		btnLoadPatient.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

		
		btnStartClass = new JButton("Start Classification");
		btnStartClass.setBackground(new Color(113, 164, 201)); 
		btnStartClass.setForeground(Color.BLACK); 
		btnStartClass.setFont(new Font("SansSerif", Font.BOLD, 14)); 
		btnStartClass.setFocusPainted(false);
		btnStartClass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		btnStartClass.setEnabled(false); 

		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setVisible(false);
		progressBar.setForeground(new Color(32, 163, 168)); 
		progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));

		
		JLabel lblDropdown = new JLabel("Select Patient:");
		lblDropdown.setFont(new Font("SansSerif", Font.BOLD, 12));
		patientDropdown = new JComboBox<>(new String[]{"No patients loaded"});
		patientDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		patientDropdown.setBackground(Color.WHITE);

		String[] dummyCases = {
				"ct_1001_image (Congenital)", 
				"Patient_ANON... (Coronary)", 
				"TAAD_122 (Type-A)"
		};
		knownDatasetList = new JList<>(dummyCases);
		knownDatasetList.setFont(new Font("SansSerif", Font.PLAIN, 12));

		JLabel lblKnownCases = new JLabel("Known Dataset Cases");
		lblKnownCases.setFont(new Font("SansSerif", Font.BOLD, 12));

		leftPanel.add(btnLoadPatient);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		leftPanel.add(btnStartClass);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		leftPanel.add(progressBar);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		leftPanel.add(lblDropdown);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		leftPanel.add(patientDropdown);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		leftPanel.add(lblKnownCases);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));

		JScrollPane listScroller = new JScrollPane(knownDatasetList);
		listScroller.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		leftPanel.add(listScroller);

		add(leftPanel, BorderLayout.WEST);
	}

	private void buildCenterPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

		JLabel titleLabel = new JLabel("2D Scan Preview", SwingConstants.LEFT);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		imageLabel = new JLabel("MIP Image Renders Here", SwingConstants.CENTER);
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.BLACK); 
		imageLabel.setForeground(Color.WHITE);
		imageLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		centerPanel.add(titleLabel, BorderLayout.NORTH);
		centerPanel.add(imageLabel, BorderLayout.CENTER);

		add(centerPanel, BorderLayout.CENTER);
	}

	private void buildRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(new Color(245, 248, 250)); 
		rightPanel.setPreferredSize(new Dimension(350, 0));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		// Initialize the dynamic value labels first
		lblValIntensity = new JLabel("N/A", SwingConstants.CENTER);
		lblValIntensity.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblValIntensity.setForeground(new Color(32, 163, 168));

		lblValDisease = new JLabel("Pending...", SwingConstants.CENTER);
		lblValDisease.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblValDisease.setForeground(new Color(32, 163, 168));

		lblValRisk = new JLabel("Pending...", SwingConstants.CENTER);
		lblValRisk.setFont(new Font("SansSerif", Font.BOLD, 22));
		lblValRisk.setForeground(new Color(204, 102, 0));

		
		JPanel pnlIntensity = createCard("Average Calcification Intensity", lblValIntensity);
		JPanel pnlDisease = createCard("Predicted Disease Category", lblValDisease);
		JPanel pnlRisk = createCard("Risk Assessment Result", lblValRisk);

		rightPanel.add(pnlIntensity);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		rightPanel.add(pnlDisease);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		rightPanel.add(pnlRisk);
		rightPanel.add(Box.createVerticalGlue());

		add(rightPanel, BorderLayout.EAST);
	}

	private void buildBottomBar() {
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(new Color(0, 86, 139)); // Navy Blue
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		lblStatus = new JLabel("System Status: Ready");
		lblStatus.setForeground(Color.WHITE);

		lblLoadedPatient = new JLabel("Loaded Patient: None");
		lblLoadedPatient.setForeground(Color.WHITE);
		lblLoadedPatient.setHorizontalAlignment(SwingConstants.CENTER);

		bottomPanel.add(lblStatus, BorderLayout.WEST);
		bottomPanel.add(lblLoadedPatient, BorderLayout.CENTER);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	private JPanel createCard(String title, JLabel valLabel) {
		JPanel card = new JPanel(new GridLayout(2, 1, 0, 5));
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
				BorderFactory.createEmptyBorder(15, 10, 15, 10)
				));

		JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
		lblTitle.setForeground(new Color(80, 80, 80));
		lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

		card.add(lblTitle);
		card.add(valLabel);
		card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

		return card;
	}

	// --- ACTION LISTENERS ---

	private void setupActionListeners() {

		
		btnLoadPatient.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only select folders

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				File selectedFolder = chooser.getSelectedFile();
				loadDataInBackground(selectedFolder.getAbsolutePath());
			}
		});

		// Dropdown Menu Selection 
		patientDropdown.addActionListener(e -> {
			if (patientDropdown.getSelectedIndex() >= 0 && !loadedPatients.isEmpty()) {
				
				currentPatient = loadedPatients.get(patientDropdown.getSelectedIndex());

				
				lblLoadedPatient.setText("Loaded Patient: " + currentPatient.getId());

				// Reset classification labels because they haven't run the algorithm on this one yet
				lblValDisease.setText("Pending...");
				lblValRisk.setText("Pending...");
				lblValDisease.setForeground(new Color(32, 163, 168)); // Reset to Teal
				lblValRisk.setForeground(new Color(204, 102, 0)); // Reset to Orange

				
				generateMIPInBackground(currentPatient);
			}
		});

		
		btnStartClass.addActionListener(e -> {
			if (currentPatient != null) {
				runClassificationInBackground();
			}
		});
	}

	// --- BACKGROUND THREADS ---

	private void loadDataInBackground(String folderPath) {
		progressBar.setVisible(true);
		lblStatus.setText("System Status: Loading Directory Data...");
		btnLoadPatient.setEnabled(false);

		SwingWorker<List<Patient>, Void> worker = new SwingWorker<>() {
			@Override
			protected List<Patient> doInBackground() throws Exception {

				PatientDatasetLoader loader = new PatientDatasetLoader();
				return loader.loadPatients(folderPath);

			}

			@Override
			protected void done() {
				try {
					
					loadedPatients = get();

					if (loadedPatients != null && !loadedPatients.isEmpty()) {

						
						patientDropdown.removeAllItems();
						for (Patient p : loadedPatients) {
							patientDropdown.addItem(p.getId());
						}

						
						patientDropdown.setEnabled(true); 
						btnStartClass.setEnabled(true);
						lblStatus.setText("System Status: " + loadedPatients.size() + " patients loaded.");

					} else {
						
						lblStatus.setText("System Status: No patients found in folder.");
						JOptionPane.showMessageDialog(Dashboard.this, "No valid patients found in the selected folder.");
					}

				} catch (Exception ex) {
					
					ex.printStackTrace();
					lblStatus.setText("System Status: Error loading data.");
				} finally {
					
					progressBar.setVisible(false);
					btnLoadPatient.setEnabled(true);
				}
			}};
			worker.execute();
	}

	private void generateMIPInBackground(Patient patient) {
		progressBar.setVisible(true);
		lblStatus.setText("System Status: Generating 2D MIP Image...");

		SwingWorker<BufferedImage, Void> worker = new SwingWorker<>() {
			@Override
			protected BufferedImage doInBackground() throws Exception {

				return MIPGenerator.generateMIP(patient.getImgSlices());

			}

			@Override
			protected void done() {
				try {
					BufferedImage mip = get();
					if (mip != null) {
						imageLabel.setIcon(new ImageIcon(mip));
						imageLabel.setText(""); // clear the placeholder text
					} else {
						imageLabel.setText("MIP Image Loaded Successfully");
					}
					lblStatus.setText("System Status: Ready");
				} catch (Exception ex) {
					ex.printStackTrace();
					lblStatus.setText("System Status: Error generating image.");
				} finally {
					progressBar.setVisible(false);
				}
			}
		};
		worker.execute();
	}

	private void runClassificationInBackground() {
		progressBar.setVisible(true);
		btnStartClass.setEnabled(false);
		lblStatus.setText("System Status: Running Graph Analytics & KNN...");
		lblValDisease.setText("Classifying...");
		lblValRisk.setText("Calculating...");

		SwingWorker<String[], Void> worker = new SwingWorker<>() {
			@Override
			protected String[] doInBackground() throws Exception {
				PatientDatasetLoader loader = new PatientDatasetLoader();

				List<Patient> knownPatients = loader.loadKnownPatients("Images/Known Des");

				KDTree tree = new KDTree();
				for (Patient p : knownPatients) {
					GraphFeatures f = p.getFeatures();
					double[] featureVector = {
							f.getAvgIntensity(),
							f.getDensity(),
							f.getAvgDegree()
					};
					tree.insert(featureVector, p);
				}

				KNNClassifier knn = new KNNClassifier();


				String riskLevel = "";
				
				String diseaseResult = knn.Classify(currentPatient, tree); 
				
				if(diseaseResult.toLowerCase().contains("aortic dissection")) {
				    riskLevel = "EMERGENCY ROOM NOW!!";
				}else {
				    double avgIntensity = currentPatient.getFeatures().getAvgIntensity();
				    
				    if (avgIntensity > 250) {
				        riskLevel = "High";
				    } else if (avgIntensity < 210) {
				        riskLevel = "Low";
				    } else {
				        // I fixed your commented-out logic here! If it's not > 250 and not < 210, 
				        // it naturally falls into the "Moderate" category without needing complex OR/AND operators.
				        riskLevel = "Moderate"; 
				    }
				}

				String intensityResult = String.format("%.2f", currentPatient.getFeatures().getAvgIntensity());
				
				
				
				
				return new String[]{intensityResult, diseaseResult, riskLevel};
			}

			@Override
			protected void done() {
				try {
					String[] results = get();

					// Update UI with results
					lblValIntensity.setText(results[0]);
					lblValDisease.setText(results[1]);
					lblValRisk.setText(results[2]);

					// Dynamic Color Coding based on Risk Assessment
					if (results[2].contains("High")) {
						lblValRisk.setForeground(new Color(204, 0, 0)); // Red for High Risk
					} else if (results[2].contains("Low")) {
						lblValRisk.setForeground(new Color(0, 153, 51)); // Green for Low Risk
					} else {
						lblValRisk.setForeground(new Color(204, 102, 0)); // Orange for Moderate Risk
					}

					lblStatus.setText("System Status: Classification Complete");
				} catch (Exception ex) {
					ex.printStackTrace();
					lblStatus.setText("System Status: Classification Failed");
				} finally {
					progressBar.setVisible(false);
					btnStartClass.setEnabled(true);
				}
			}
		};
		worker.execute();}
		
		private void setupDragAndDrop() {
	        // Attach a DropTarget to the entire JFrame
	        new DropTarget(this, new DropTargetAdapter() {
	            @Override
	            public void drop(DropTargetDropEvent event) {
	                try {
	                    // Accept the dropped file
	                    event.acceptDrop(DnDConstants.ACTION_COPY);
	                    Transferable transferable = event.getTransferable();

	                    // Check if the dropped item is a file/folder list
	                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
	                        
	                        // Suppress unchecked cast warning for the file list
	                        @SuppressWarnings("unchecked")
	                        java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

	                        if (!files.isEmpty()) {
	                            File droppedItem = files.get(0);
	                            
	                            // Check if they dropped a folder (like the master "Patients" folder)
	                            if (droppedItem.isDirectory()) {
	                                // Trigger your exact same background loader!
	                                loadDataInBackground(droppedItem.getAbsolutePath());
	                            } else {
	                                JOptionPane.showMessageDialog(Dashboard.this, 
	                                    "Please drag and drop a Patient directory, not an individual file.", 
	                                    "Invalid Drop", JOptionPane.WARNING_MESSAGE);
	                            }
	                        }
	                    }
	                    event.dropComplete(true);
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    event.dropComplete(false);
	                }
	            }
	        });
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			new Dashboard().setVisible(true);
		});
	}
}
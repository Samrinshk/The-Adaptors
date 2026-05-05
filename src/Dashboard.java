import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class Dashboard extends JFrame {

    // Backend Variables
    private Patient currentPatient;
    private List<Patient> loadedPatientsList; // Stores all loaded patients
    
    // UI Components
    private JLabel imageLabel;
    private JProgressBar progressBar;
    private JLabel patientIdLabel;
    private JLabel sliceCountLabel;
    private JLabel diseaseLabel;
    private JComboBox<String> patientDropdown; // New Dropdown Menu
    
    public Dashboard() {
        // 1. Set up the Main Window
        setTitle("The Adaptors: CT Calcification Analyzer");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 2. Top Panel (Header)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(45, 52, 54));
        JLabel titleLabel = new JLabel("CT Scan Disease Classifier");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // 3. Left Panel (Controls)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton loadButton = new JButton("Load Patient Folder");
        
        // --- NEW: Dropdown Menu Setup ---
        JLabel menuLabel = new JLabel("Select Patient:");
        patientDropdown = new JComboBox<>();
        patientDropdown.setEnabled(false); // Disabled until patients are loaded
        patientDropdown.setMaximumSize(new Dimension(200, 30));
        
        JButton classifyButton = new JButton("Run Classification");
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        
        leftPanel.add(loadButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(menuLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(patientDropdown);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(classifyButton);
        leftPanel.add(Box.createVerticalStrut(30));
        leftPanel.add(progressBar);
        add(leftPanel, BorderLayout.WEST);
        
        // 4. Center Panel (The Visualizer)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);
        imageLabel = new JLabel("No Image Loaded", SwingConstants.CENTER);
        imageLabel.setForeground(Color.DARK_GRAY);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        
        // 5. Right Panel (Patient Details)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel infoHeader = new JLabel("Patient Information:");
        infoHeader.setFont(new Font("Arial", Font.BOLD, 16));
        
        patientIdLabel = new JLabel("Patient ID: ---");
        sliceCountLabel = new JLabel("Total Slices: ---");
        
        diseaseLabel = new JLabel("Predicted Disease: ---");
        diseaseLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        rightPanel.add(infoHeader);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(patientIdLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(sliceCountLabel);
        rightPanel.add(Box.createVerticalStrut(40));
        rightPanel.add(diseaseLabel);
        add(rightPanel, BorderLayout.EAST);
        
        // ---------------------------------------------------------
        // ACTION LISTENERS
        // ---------------------------------------------------------
        
        // Load Folder Action
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                
                int result = fileChooser.showOpenDialog(Dashboard.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();
                    String folderPath = selectedFolder.getAbsolutePath();
                    
                    progressBar.setVisible(true);
                    patientDropdown.setEnabled(false);
                    
                    SwingWorker<List<Patient>, Void> worker = new SwingWorker<List<Patient>, Void>() {
                        @Override
                        protected List<Patient> doInBackground() throws Exception {
                            PatientDatasetLoader datasetLoader = new PatientDatasetLoader();
                            return datasetLoader.loadPatients(folderPath);
                        }

                        @Override
                        protected void done() {
                            progressBar.setVisible(false);
                            try {
                                loadedPatientsList = get();
                                if (loadedPatientsList != null && !loadedPatientsList.isEmpty()) {
                                    // Clear old items and populate the dropdown menu
                                    patientDropdown.removeAllItems();
                                    for (Patient p : loadedPatientsList) {
                                        patientDropdown.addItem(p.getId());
                                    }
                                    patientDropdown.setEnabled(true); // Turn menu on
                                } else {
                                    JOptionPane.showMessageDialog(Dashboard.this, "No valid patients found in selected folder.");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    };
                    worker.execute();
                }
            }
        });
        
        // --- NEW: Dropdown Menu Selection Action ---
        patientDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (patientDropdown.getSelectedItem() != null && loadedPatientsList != null) {
                    String selectedId = (String) patientDropdown.getSelectedItem();
                    
                    // Find the matched patient from our loaded list
                    for (Patient p : loadedPatientsList) {
                        if (p.getId().equals(selectedId)) {
                            currentPatient = p;
                            break;
                        }
                    }
                    
                    if (currentPatient != null) {
                        // Generate their 2D image and update the UI
                        progressBar.setVisible(true);
                        
                        SwingWorker<BufferedImage, Void> renderWorker = new SwingWorker<BufferedImage, Void>() {
                            @Override
                            protected BufferedImage doInBackground() throws Exception {
                                return MIPGenerator.generateMIP(currentPatient.getImgSlices());
                            }

                            @Override
                            protected void done() {
                                progressBar.setVisible(false);
                                try {
                                    BufferedImage mipImage = get();
                                    if (mipImage != null) {
                                        imageLabel.setText(""); 
                                        imageLabel.setIcon(new ImageIcon(mipImage));
                                        
                                        // Update labels for the new patient
                                        patientIdLabel.setText("Patient ID: " + currentPatient.getId());
                                        sliceCountLabel.setText("Total Slices: " + currentPatient.getImgSlices().size());
                                        
                                        // Reset classification label
                                        diseaseLabel.setText("Predicted Disease: Pending...");
                                        diseaseLabel.setForeground(Color.BLACK);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        };
                        renderWorker.execute();
                    }
                }
            }
        });
        
        // Run Classification Action
        classifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPatient == null) {
                    JOptionPane.showMessageDialog(Dashboard.this, "Please select a patient first!");
                    return;
                }
                
                progressBar.setVisible(true);
                diseaseLabel.setText("Predicted Disease: Classifying...");
                diseaseLabel.setForeground(Color.BLACK);
                
                SwingWorker<String, Void> mlWorker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        PatientDatasetLoader loader = new PatientDatasetLoader();
                        // Make sure this folder exists inside your project!
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
                        return knn.Classify(currentPatient, tree);
                    }
                    
                    @Override
                    protected void done() {
                        progressBar.setVisible(false);
                        try {
                            String verdict = get();
                            currentPatient.setCategory(verdict);
                            
                            diseaseLabel.setText("Predicted Disease: " + verdict);
                            
                            if(verdict.equalsIgnoreCase("High Risk") || verdict.toLowerCase().contains("severe")) {
                                diseaseLabel.setForeground(Color.RED);
                            } else {
                                diseaseLabel.setForeground(new Color(0, 153, 0)); // Dark Green
                            }
                            
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            diseaseLabel.setText("Predicted Disease: Error");
                        }
                    }
                };
                mlWorker.execute();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }
}
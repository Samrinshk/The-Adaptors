/**
 * This class will extract the pixel values and categorize them based on calsification intensity
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class GraphMapper {

	/**
	 * we will start by passing the loaded images from the image loader 
	 * into this class
	 */

	private BufferedImage [] loadedimages;
	private int threshold = 200;
	
	private int steps = 4;

	//the constructer that recives the array of loaded images from the imageloader class
	public GraphMapper(BufferedImage[] images) {
		this.loadedimages = images;
	}


	/**
	 * we will implement a triple nested loop to traverse the 3D space
	 * @return 
	 */
	public RAG translateToGraph() {
		RAG rag = new RAG();
		//stop the loop here if the loadedimages array is empty
		if(loadedimages == null) {
			return rag;
		}
		
		List<RegionNode> createdNodes = new ArrayList<>();
		int calcificationCount = 0;
		/*
		 * this is the triple nested loop which will traverse the 3D space
		 */
		for (int z = 0; z < loadedimages.length; z++ )//outer loop going through the slices
		{
			//skip null inages safely
			if(loadedimages[z] == null) {
				continue;
			}

			BufferedImage currentslice = loadedimages[z];

			int width = currentslice.getWidth();
			int height = currentslice.getHeight();

			for (int y = 0; y < height; y += steps) {

				for(int x = 0; x < width ; x += steps) {

					//getting the greyscale value since the images are already in greyscale we can acces the gryscale value dirctly 

					int rgb = currentslice.getRGB(x, y);
					//since the images are already in greyscale we only need to get the last 8 bits which will give us the intensity
					int intensity = rgb & 0xFF;

					//this is where we would call the addNode method but only if the intensity is above the threshhold value
					if(intensity > threshold) {
						RegionNode newnode = new RegionNode(x,y,z, intensity);//creating the new node 
						//Here we will add the node to the graph
						rag.addNode(newnode);
						createdNodes.add(newnode);
						//System.out.println(" calcification at " + x+ ", " + y + ", " + z);
						calcificationCount++;
					}
				}
			}
		}
		
		// prints summary of every calcification
		System.out.println("Total Calcifications found: " + calcificationCount);
		//temporarilty commented out
		connectAdjacentNodes(rag,createdNodes);
		return rag;

	}

	private void connectAdjacentNodes(RAG rag, List<RegionNode> nodes) {
		for(int i = 0; i < nodes.size(); i++) {
			RegionNode a = nodes.get(i);
			
			for(int j = i + 1; j< nodes.size();j++) {
				RegionNode b = nodes.get(j);
				
				if(isAdjecent(a,b)) {
					double weight = Math.abs(a.getIntensity() - b.getIntensity());
					rag.addEdges(a, b, weight);
				}
			}
		}
	}


	private boolean isAdjecent(RegionNode a, RegionNode b) {
		int dx = Math.abs((int)a.getX()- (int)b.getX());
		int dy = Math.abs((int)a.getY()- (int)b.getY());
		int dz = Math.abs((int)a.getZ()- (int)b.getZ());
		
		return dx <= 1 && dy <=1 && dz<= 1 && !(dx==0 && dy==0 && dz==0);
		
	}
}

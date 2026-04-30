import java.util.List;

public class FeatureExtractor {

	/**
	 * this fuction is used to extract th emain graph features from a RAG
	 * These features will be used in the KDTrees and the KNN
	 */
	public GraphFeatures extractFeatures(RAG rag) {
		int nodeCount = rag.getNodeCount();
		int edgeCount = rag.getEdgeCount();
		
		double avgIntensity = calculateAverageIntensity(rag.getAllNodes());
		
		// calc average graph degree
		double avgDegree = calculateAverageDegree(nodeCount, edgeCount);
		
		//calc graph density
		double density = calculateDensity(nodeCount, edgeCount);
		
		//returns the exttracted freature summary 
		 return new GraphFeatures(nodeCount, edgeCount, avgIntensity, avgDegree, density);
	}
	
	/**
	 * Calculates the average intensity of all nodes in the graph.
	 * @param nodes
	 * @return the average node intensity
	 */
	private double calculateAverageIntensity(List<RegionNode> nodes) {
		if(nodes == null || nodes.isEmpty()) {
			return 0;
		}
		
		double totalIntensity = 0;
		
		for(RegionNode node :nodes) {
			totalIntensity += node.getIntensity();
		}
		
		return totalIntensity / nodes.size();
	}
	
	/**
	 * this function calculates the average degree of an undirected graph
	 * @param nodeCount
	 * @param edgeCount
	 * @return average degree
	 */
	private double calculateAverageDegree(int nodeCount, int edgeCount){
		if(nodeCount == 0) {
			return 0;
		}
		return (2.0 * edgeCount) / nodeCount;
		
	}
	
	private double calculateDensity(int nodeCount, int edgeCount) {
		if(nodeCount <= 1) {
			return 0;
		}
		
		double possibleEdges = (double) nodeCount*(nodeCount -1);
		return (2.0 * edgeCount) / possibleEdges;
	}
}

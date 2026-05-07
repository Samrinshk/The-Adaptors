package logic;

import java.util.List;

import structures.RAG;
import structures.RegionNode;

/**
 * Responsible for converting a RAG into numerical features
 */
public class FeatureExtractor {

	/**
	 * this fuction is used to extract the main graph features from a RAG
	 * These features will be used in the KDTrees and the KNN
	 * 
	 * @param rag - structure to analyze 
	 * @return - object with the data
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
	 * 
	 * @param nodes - list of nodes
	 * @return - the average node intensity
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
	 * 
	 * @param nodeCount - total number of regions
	 * @param edgeCount - total number of adjacencies
	 * @return average number of connections per node 
	 */
	private double calculateAverageDegree(int nodeCount, int edgeCount){
		if(nodeCount == 0) {
			return 0;
		}
		return (2.0 * edgeCount) / nodeCount;
		
	}
	
	/**
	 * Calculates graph density 
	 * 
	 * @param nodeCount - total number of regions
	 * @param edgeCount - total number of adjacencies
	 * @return - value between 0 and 1 to represnet graph completeness
	 */
	private double calculateDensity(int nodeCount, int edgeCount) {
		if(nodeCount <= 1) {
			return 0;
		}
		
		double possibleEdges = (double) nodeCount*(nodeCount -1);
		return (2.0 * edgeCount) / possibleEdges;
	}
}

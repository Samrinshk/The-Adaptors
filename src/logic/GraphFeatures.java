package logic;

/**
 * Stores numerical representation of RAG
 */
public class GraphFeatures {
	private int nodeCount;
	private int edgeCount;
	private double avgIntensity;
	private double avgDegree;
	private double density;
	
	/**
	 * Constructor for the GraphFeatures class
	 * 
	 * @param nodeCount - total number of nodes
	 * @param edgeCount - total number of edges
	 * @param avgIntensity - average intensity across the graph
	 * @param avgDegree - average degree
	 * @param density - ratio of edges and max edges
	 */
	public GraphFeatures(int nodeCount, int edgeCount, double avgIntensity, double avgDegree, double density) {
		super();
		this.nodeCount = nodeCount;
		this.edgeCount = edgeCount;
		this.avgIntensity = avgIntensity;
		this.avgDegree = avgDegree;
		this.density = density;
	}
	
	public int getNodeCount() {
		return nodeCount;
	}
	
	public int getEdgeCount() {
		return edgeCount;
	}
	
	public double getAvgIntensity() {
		return avgIntensity;
	}
	
	public double getAvgDegree() {
		return avgDegree;
	}
	
	public double getDensity() {
		return density;
	}
	/**
	 * Returns a string representation
	 */
	@Override
	public String toString() {
		return "GraphFeatures [nodeCount=" + nodeCount + ", edgeCount=" + edgeCount + ", avgIntensity=" + avgIntensity
				+ ", avgDegree=" + avgDegree + ", density=" + density + "]";
	}
	
	
	
}

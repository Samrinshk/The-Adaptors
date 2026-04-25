
public class GraphFeatures {
	private int nodeCount;
	private int edgeCount;
	private double avgIntensity;
	private double avgDegree;
	
	private double density;
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
	@Override
	public String toString() {
		return "GraphFeatures [nodeCount=" + nodeCount + ", edgeCount=" + edgeCount + ", avgIntensity=" + avgIntensity
				+ ", avgDegree=" + avgDegree + ", density=" + density + "]";
	}
	
	
	
}

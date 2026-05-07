package structures;

/**
 * This class:
 * used to represent weighted connection between two RegionNode Objects
 * in RAG
 * @see RegionNode.java, KDNode.java, KDTree.java, RAG.java, RegionNode.javas
 */
public class GraphEdge {
	private RegionNode source;
	private RegionNode destination;
	private double weight;
	
	/**
	 * Contructor for graphedge class
	 * 
	 * @param source
	 * @param destination
	 * @param weight
	 */
	public GraphEdge(RegionNode source, RegionNode destination, double weight) {
		super();
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public RegionNode getSource() {
		return source;
	}

	public void setSource(RegionNode source) {
		this.source = source;
	}

	public RegionNode getDestination() {
		return destination;
	}

	public void setDestination(RegionNode dsestination) {
		this.destination = dsestination;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "GraphEdge [source=" + source + ", destination=" + destination + ", weight=" + weight + "]";
	}
	
	
	
	

}

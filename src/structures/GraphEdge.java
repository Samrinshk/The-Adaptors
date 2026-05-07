package structures;
public class GraphEdge {
	private RegionNode source;
	private RegionNode destination;
	private double weight;
	
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

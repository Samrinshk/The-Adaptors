import java.util.ArrayList;
import java.util.List;

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * Extends your graph logic to connect spatial regions in the CT scans.
 */

public class RAG 
{
	private List<RegionNode> nodes;
	private List<GraphEdge> edges;
	
	public RAG() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		
	}
	
	public void addNode(RegionNode node) {
		nodes.add(node);
		
	}
	
	public void addEdges(RegionNode source, RegionNode destination, double weight) {
		edges.add(new GraphEdge(source,destination, weight));
	}

	public List<RegionNode> getNodes() {
		return nodes;
	}

	public List<GraphEdge> getEdges() {
		return edges;
	}
	
	public int getNodeCount(){
		return nodes.size();
	}
	
	public int getEdgeCount() {
		return edges.size();
	}

}

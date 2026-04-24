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
	
	//Node CRUD 
	public void addNode(RegionNode node) {
		nodes.add(node);
		
	}
	
	public RegionNode getNode(int id) {
		for(RegionNode n : nodes) {
			if (n.getId() == id) {
				return n;
			}
		}
		return null;
	}
	
	public List<RegionNode> getAllNodes() {
		return nodes;
	}
	
	public int getNodeCount(){
		return nodes.size();
	}
	
	public void updateNode (RegionNode updatedNode){
		for (int i = 0; i < nodes.size(); i++){
			if (nodes.get(i).getId() == updatedNode.getId()) {
				nodes.set(i, updatedNode);
				return;
			}
		}
	}
	
	public void removeNode (int nodeId) {
		RegionNode toRemove = getNode(nodeId);
		if (toRemove != null) {
            edges.removeIf(e -> e.getSource().equals(toRemove) || e.getDestination().equals(toRemove));
			nodes.remove(toRemove);
		}
	}
	
	//Edge CRUD
	public void addEdge(RegionNode source, RegionNode destination, double weight) {
		edges.add(new GraphEdge(source,destination, weight));
	}

	public GraphEdge getEdge (int fromId, int toId ) {
		for (GraphEdge e : edges) {
			if (e.getSource().getId() == fromId &&
				e.getDestination().getId() == toId) {
				return e;
			}
		}
		return null;
	}
	
	public List<GraphEdge> getAllEdges() {
		return edges;
	}
		
	public int getEdgeCount() {
		return edges.size();
	}

	public void updateEdgeWeight (int fromId, int toId, double weight) {
		GraphEdge e = getEdge(fromId, toId);
		if (e != null) {
			e.setWeight(weight);
		}
	}
	
	public void removeEdge (int fromId, int toId) {
		edges.removeIf(e -> e.getSource().getId() == fromId && e.getDestination().getId() == toId);
	}
	
}

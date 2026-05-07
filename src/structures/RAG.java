package structures;
import java.util.ArrayList;
import java.util.List;

/**
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
	/**
	 * Adds region node to graph
	 * @param node
	 */
	public void addNode(RegionNode node) {
		nodes.add(node);
		
	}
	
	/**
	 * Gets node from graph using unique id
	 * @param id
	 * @return
	 */
	public RegionNode getNode(int id) {
		for(RegionNode n : nodes) {
			if (n.getId() == id) {
				return n;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return list of all region nodes
	 */
	public List<RegionNode> getAllNodes() {
		return nodes;
	}
	
	/**
	 * 
	 * @return node count
	 */
	public int getNodeCount(){
		return nodes.size();
	}
	
	/**
	 * Updates an existing node in graph
	 * @param updatedNode
	 */
	public void updateNode (RegionNode updatedNode){
		for (int i = 0; i < nodes.size(); i++){
			if (nodes.get(i).getId() == updatedNode.getId()) {
				nodes.set(i, updatedNode);
				return;
			}
		}
	}
	
	/**
	 * remove node and all connected edges from the graph
	 * @param nodeId
	 */
	public void removeNode (int nodeId) {
		RegionNode toRemove = getNode(nodeId);
		if (toRemove != null) {
            edges.removeIf(e -> e.getSource().equals(toRemove) || e.getDestination().equals(toRemove));
			nodes.remove(toRemove);
		}
	}
	
	//Edge CRUD
	/**
	 * adds weighted edge between two region nodes
	 * @param source
	 * @param destination
	 * @param weight
	 */
	public void addEdge(RegionNode source, RegionNode destination, double weight) {
		edges.add(new GraphEdge(source,destination, weight));
	}

	/**
	 * gets edge connecting two nodes
	 * @param fromId
	 * @param toId
	 * @return
	 */
	public GraphEdge getEdge (int fromId, int toId ) {
		for (GraphEdge e : edges) {
			if (e.getSource().getId() == fromId &&
				e.getDestination().getId() == toId) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return list of graph edges
	 */
	public List<GraphEdge> getAllEdges() {
		return edges;
	}
		
	/**
	 * @return edge count
	 */
	public int getEdgeCount() {
		return edges.size();
	}

	/**
	 * updates weight value of an existing edge
	 * @param fromId
	 * @param toId
	 * @param weight
	 */
	public void updateEdgeWeight (int fromId, int toId, double weight) {
		GraphEdge e = getEdge(fromId, toId);
		if (e != null) {
			e.setWeight(weight);
		}
	}
	
	/**
	 * removes edge connecting two nodes
	 * @param fromId
	 * @param toId
	 */
	public void removeEdge (int fromId, int toId) {
		edges.removeIf(e -> e.getSource().getId() == fromId && e.getDestination().getId() == toId);
	}
	
}

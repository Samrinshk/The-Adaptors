package structures;

/**
 * Interface for a Graph Node. 
 * Essential for translating image data into graph constructs
 * @see GraphEdge.java, KDNode.java, KDTree.java, RAG.java, RegionNode.java
 */
public interface INode<T> 
{
	T getElement();						//gets the data element stored in the node
	void setElement(T element);			//updates data element stored in the node
	
	int getId();
	double getX();						//returns the width of the region
	double getY();						//returns the height of the region 
	double getZ();						//returns the depth of the region
	
	double getAvgIntensity();			//returns average pixel intensity for this region
	
	@Override 
	String toString();					//returns string representation of the node 
	
	
}

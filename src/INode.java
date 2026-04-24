/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * Interface for a Graph Node. 
 * Essential for translating image data into graph constructs
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

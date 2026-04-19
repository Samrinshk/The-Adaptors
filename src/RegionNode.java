/**
 * The pixels which reach the intensity threshold will be represented by the attributes in this class
 */
public class RegionNode {

	//These are where the coordinates of each node will ge stored
	private int x;
	private int y;
	private int z;
	
	private int intensity;//this is the greyscale value it is >200 but it will also help in similarity detection
	
	
	public RegionNode(int x, int y, int z, int intensity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.intensity = intensity;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getZ() {
		return z;
	}


	public void setZ(int z) {
		this.z = z;
	}


	public int getIntensity() {
		return intensity;
	}


	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}
	
	
	
}

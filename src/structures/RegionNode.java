package structures;

/**
 * The pixels which reach the intensity threshold will be represented by the attributes in this class
 */
public class RegionNode implements INode<Integer> {

	//These are where the coordinates of each node will ge stored
	private int id;
	private int x;
	private int y;
	private int z;
	
	private int intensity;//this is the greyscale value it is >200 but it will also help in similarity detection
	
	
	/**
	 * @param id
	 * @param x
	 * @param y
	 * @param z
	 * @param intensity
	 */
	public RegionNode(int id, int x, int y, int z, int intensity) {
		this.id =id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.intensity = intensity;
	}


	@Override
	public Integer getElement() {
		
		return intensity;
	}


	@Override
	public void setElement(Integer element) {
		this.intensity = element;
		
	}

	
	@Override
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}


	@Override
	public double getX() {
		
		return x;
	}

	

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public double getY() {
		
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	@Override
	public double getZ() {
		
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public double getAvgIntensity() {
		
		return intensity;
	}


	public int getIntensity() {
		return intensity;
	}


	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}


	@Override
	public String toString() {
		return "RegionNode [x=" + x + ", y=" + y + ", z=" + z + ", intensity=" + intensity + "]";
	}


	
	
	
}

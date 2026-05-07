package structures;
import data.Patient;

public class KDNode {
	double[] point;
	
	private Patient patient;
	
	private KDNode left;
	private KDNode right;
	
	/**
	 * Constructor for new KD-Tree Node
	 * @param point 
	 * @param patient
	 * @param left
	 * @param right
	 */
	public KDNode(double[] point, Patient patient) {
		this.point = point;
		this.patient = patient;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * @return the point
	 */
	public double[] getPoint() {
		return point;
	}
	
	/**
	 * @param point the point to set
	 */
	public void setPoint(double[] point) {
		this.point = point;
	}

	/**
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * @return the left
	 */
	public KDNode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(KDNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public KDNode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(KDNode right) {
		this.right = right;
	}

}

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Thato: 224022442
 * Refiloe: 221014292
 * Samrin: 222005020
 * Naledi: 218104732
 * 
 * The structure that will help you find the "nearest neighbors" for your KNN graph efficiently.
 */

public class KDTree 
{
	private KDNode root;		//Top of the tree
	private final int K = 3; 	//for the 3 dimensions: Intensity, Density, Degree
	
	//Insert patient into the KD-Tree
	public void insert(double[] point, Patient patient)
	{
		//Call the recurive method 
		root = insertRecursive(root, point, patient, 0);
	}

	/**
	 * Recursive insert - used to find the right empty spot 
	 * 
	 * @param node
	 * @param point
	 * @param patient
	 * @param depth
	 * @return
	 */
	private KDNode insertRecursive(KDNode node, double[] point, Patient patient, int depth) 
	{
		//if we reach a null, create a new node
		if (node == null)
		{
			return new KDNode(point, patient);
		}
		
		//Current dimension -> decides which feature to compare
		int cd = depth % K;
		
		//Compare the new patient's feature to the current node's feature
		if(point[cd] < node.getPoint()[cd])
		{
			//if smaller place it on the left branch 
			node.setLeft(insertRecursive(node.getLeft(), point, patient, depth + 1));
		}
		else
		{
			//if larger place it on the right branch 
			node.setRight(insertRecursive(node.getRight(), point, patient, depth + 1));
		}
		
		//return node to keep the tree linked 
		return node;
	}
	
	/**
	 * Calculates how far apart the 2 features are 
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public double distance (double[] a, double[] b)
	{
		double sum = 0;
		
		for(int i =0; i < K; i++)
		{
			//FeatureA - FeatureB ^ squared 
			sum += Math.pow(a[i] - b[i], 2);
		}
		
		return Math.sqrt(sum);
	}
	
	/**
	 * Find the K most similar patients by checking the tree
	 * 
	 * @param target
	 * @param k
	 * @return
	 */
	public List<Patient> findKNearest(double[] target, Patient targetPatient, int k)
	{
		//Create a PQ that ranks by distance 
		PriorityQueue<NodeDistance> pq = new PriorityQueue<>(new Comparator<NodeDistance>() {
			
			@Override
			public int compare(NodeDistance n1, NodeDistance n2) {
				
				if (n1.distance <n2.distance)
				{
					return -1; //n1 is closer 
				}
				
				if (n1.distance > n2.distance)
				{
					return 1; //n2 is further
				}
				
				return 0;
			}
		});
		// Check every node in the tree
		collectAllNodes(root, target,targetPatient,pq);
		
		//Fill the PQ 
		//searchRecursive (root, target, 0, pq);
		
		//Pull the top 'k' patients
		List<Patient> results = new ArrayList<>();
		
		for (int i = 0; i < k && !pq.isEmpty(); i++)
		{
			results.add(pq.poll().node.getPatient());
		}
				
		return results;
	}
	
	private void collectAllNodes(KDNode node, double[] target, Patient targetPatient, PriorityQueue<NodeDistance> pq){
		if(node == null) {
			return;
		}
		
		if(targetPatient == null || !node.getPatient().getId().equals(targetPatient.getId())) {
			double newDist = distance(target, node.getPoint());
			pq.add(new NodeDistance(node, newDist));
		}
		
		collectAllNodes(node.getLeft(), target, targetPatient, pq);
		collectAllNodes(node.getRight(), target, targetPatient, pq);
		
	}

	/**
	 * @param node
	 * @param target
	 * @param depth
	 * @param pq
	 */
	private void searchRecursive(KDNode node, double[] target, int depth, PriorityQueue<NodeDistance> pq) 
	{
		if (node == null)
		{
			return;
		}
		
		//Add this node to the sorting queue
		double newDist = distance(target, node.getPoint());
		pq.add(new NodeDistance(node, newDist));
		
		//Keep searching down the tree
		int cd = depth % K;
		
		//Compare the new patient's feature to the current node's feature
		if(target[cd] < node.getPoint()[cd])
		{
			//if smaller place it on the left branch 
			searchRecursive(node.getLeft(), target, depth + 1, pq);
		}
		else
		{			
			//if larger place it on the right branch 
			searchRecursive(node.getRight(), target, depth + 1, pq);
		}
	}

	//Helper class
	private class NodeDistance {
		KDNode node;
		double distance;
		
		/**
		 * @param node
		 * @param distance
		 */
		public NodeDistance(KDNode n, double d) {
			this.node = n;
			this.distance = d;
		}
	}
}

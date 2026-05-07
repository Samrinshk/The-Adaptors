package ui;

import javax.swing.*;

import structures.GraphEdge;
import structures.RAG;
import structures.RegionNode;

import java.awt.*;
import java.util.List;

/**
 * GraphVisualiser class; used to display segmented image region relationships in a graphical format
 * Visually displays a RAG
 * @see Dashboard.java, MIPGenerator.java, MIPTester.java
 */
public class GraphVisualizer extends JPanel {

	private RAG rag;

	/**
	 * Constructs graphvisualiser panel and sets its 
	 * appearance, size, background colour, and border styling
	 */
	public GraphVisualizer() {
		setPreferredSize(new Dimension(320, 260));
		setBackground(new Color(20, 24, 28));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(60, 70, 80), 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
		));
	}

	public void setGraph(RAG rag) {
		this.rag = rag;
		repaint();
	}

	/**
	 * Paints the graph visualisation onto the panel (JPanel)
	 * The graph coordinates are scaled dynamically to fit the panel.
	 * @param Graphics g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Title
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("SansSerif", Font.BOLD, 15));
		g2.drawString("Graph Representation", 10, 20);

		if (rag == null || rag.getAllNodes() == null || rag.getAllNodes().isEmpty()) {
			g2.setColor(new Color(170, 180, 190));
			g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
			g2.drawString("No graph loaded yet", 10, 50);
			return;
		}

		List<RegionNode> nodes = rag.getAllNodes();
		List<GraphEdge> edges = rag.getAllEdges();

		int panelWidth = getWidth();
		int panelHeight = getHeight();

		int drawLeft = 15;
		int drawTop = 35;
		int drawWidth = panelWidth - 30;
		int drawHeight = panelHeight - 70;

		double maxX = 1;
		double maxY = 1;

		for (int i = 0; i < nodes.size(); i++) {
			RegionNode node = nodes.get(i);

			if (node.getX() > maxX) {
				maxX = node.getX();
			}

			if (node.getY() > maxY) {
				maxY = node.getY();
			}
		}

		// Draw edge count / node count
		g2.setColor(new Color(170, 180, 190));
		g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
		g2.drawString("Nodes: " + nodes.size() + "   Edges: " + edges.size(), 10, panelHeight - 12);

		// Sample edges if there are too many
		int edgeStep = 1;
		if (edges.size() > 4000) {
			edgeStep = Math.max(1, edges.size() / 4000);
		}

		g2.setColor(new Color(90, 140, 190, 70));

		for (int i = 0; i < edges.size(); i += edgeStep) {
			GraphEdge edge = edges.get(i);

			// If your GraphEdge uses different getter names,
			// replace these two lines with your actual method names.
			RegionNode a = edge.getSource();
			RegionNode b = edge.getDestination();
			
			int x1 = drawLeft + (int) ((a.getX() / maxX) * drawWidth);
			int y1 = drawTop + (int) ((a.getY() / maxY) * drawHeight);

			int x2 = drawLeft + (int) ((b.getX() / maxX) * drawWidth);
			int y2 = drawTop + (int) ((b.getY() / maxY) * drawHeight);

			g2.drawLine(x1, y1, x2, y2);
		}

		// Sample nodes if there are too many
		int nodeStep = 1;
		if (nodes.size() > 8000) {
			nodeStep = Math.max(1, nodes.size() / 8000);
		}

		for (int i = 0; i < nodes.size(); i += nodeStep) {
			RegionNode node = nodes.get(i);

			int x = drawLeft + (int) ((node.getX() / maxX) * drawWidth);
			int y = drawTop + (int) ((node.getY() / maxY) * drawHeight);

			int intensity = node.getIntensity();
			int red = Math.min(255, 100 + (intensity / 2));
			int green = Math.max(70, 220 - intensity / 2);

			g2.setColor(new Color(red, green, 90));
			g2.fillOval(x - 2, y - 2, 5, 5);
		}
	}
}

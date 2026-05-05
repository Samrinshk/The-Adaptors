import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphVisualizer extends JPanel {
    
    private RAG currentRag;
    
    private static final int MAX_DRAW_LIMIT = 8000; 

    public GraphVisualizer() {
        setBackground(Color.BLACK);
    }

    public void setGraph(RAG rag) {
        this.currentRag = rag;
        repaint(); // Force the panel to redraw when a new graph is loaded
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentRag == null || currentRag.getNodeCount() == 0) {
            g.setColor(Color.DARK_GRAY);
            g.drawString("No Graph Data Loaded", getWidth() / 2 - 60, getHeight() / 2);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<RegionNode> nodes = currentRag.getAllNodes();
        List<GraphEdge> edges = currentRag.getAllEdges();

        
        double maxX = 1;
        double maxY = 1;
        for (RegionNode n : nodes) {
            if (n.getX() > maxX) maxX = n.getX();
            if (n.getY() > maxY) maxY = n.getY();
        }
        
        
        double scaleX = (getWidth() - 40) / maxX;
        double scaleY = (getHeight() - 40) / maxY;

        
        g2d.setColor(new Color(100, 150, 255, 50)); // Semi-transparent light blue
        int edgeCount = 0;
        for (GraphEdge edge : edges) {
            if (edgeCount++ > MAX_DRAW_LIMIT) break; // Safety stop

            RegionNode source = edge.getSource();
            RegionNode dest = edge.getDestination();

            int x1 = (int) (source.getX() * scaleX) + 20;
            int y1 = (int) (source.getY() * scaleY) + 20;
            int x2 = (int) (dest.getX() * scaleX) + 20;
            int y2 = (int) (dest.getY() * scaleY) + 20;

            g2d.drawLine(x1, y1, x2, y2);
        }

        
        g2d.setColor(Color.RED);
        int nodeCount = 0;
        for (RegionNode node : nodes) {
            if (nodeCount++ > MAX_DRAW_LIMIT) break; // Safety stop

            int x = (int) (node.getX() * scaleX) + 20;
            int y = (int) (node.getY() * scaleY) + 20;
            
            
            g2d.fillOval(x - 2, y - 2, 4, 4);
        }
        
        // Add a warning label if we hit the limit
        if (nodes.size() > MAX_DRAW_LIMIT || edges.size() > MAX_DRAW_LIMIT) {
             g2d.setColor(Color.YELLOW);
             g2d.drawString("Warning: Graph too large. Only displaying partial rendering.", 10, 20);
        }
    }
}

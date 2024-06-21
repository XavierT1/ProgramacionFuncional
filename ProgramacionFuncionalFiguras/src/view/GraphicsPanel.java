package view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import controller.GraphicsController;

public class GraphicsPanel extends JPanel {
    private String shape = "";
    private GraphicsController graphicsController;

    public GraphicsPanel(GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
    }

    public void setShape(String shape) {
        this.shape = shape;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        graphicsController.drawShape(shape, g);
    }
}

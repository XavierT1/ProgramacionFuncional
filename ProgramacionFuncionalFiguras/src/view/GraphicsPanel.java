package view;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import model.IShapeGraphics;

public class GraphicsPanel extends JPanel {
    private IShapeGraphics shapeGraphics;

    public GraphicsPanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(400, 400));
    }

    public void setShapeGraphics(IShapeGraphics shapeGraphics) {
        this.shapeGraphics = shapeGraphics;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (shapeGraphics != null) {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int shapeSize = 200;

            int x = centerX - shapeSize / 2;
            int y = centerY - shapeSize / 2;

            shapeGraphics.drawShape(g, x, y);
        }
    }
}

package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.IShapeGraphics;
import controller.GraphicsController;

/**
 * ControlPanel class to manage the UI components and interactions.
 * Author: Freddy Tapia
 */

public class ControlPanel extends JPanel {
    private GraphicsPanel shapeCanvas;
    private GraphicsController graphicsController;

    public ControlPanel() {
        setLayout(new BorderLayout());
        injectDependencies();

        shapeCanvas = new GraphicsPanel();

        JButton circleButton = new JButton("Draw Circle");
        JButton triangleButton = new JButton("Draw Triangle");
        JButton squareButton = new JButton("Draw Square");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(circleButton);
        buttonPanel.add(triangleButton);
        buttonPanel.add(squareButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(shapeCanvas, BorderLayout.CENTER);

        circleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawShape((g, x, y) -> {
                    g.setColor(java.awt.Color.BLUE);
                    int diameter = 200;
                    g.fillOval(x, y, diameter, diameter);
                });
            }
        });

        triangleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                drawShape((g, x, y) -> {
                    g.setColor(java.awt.Color.GREEN);

                    int base = 200;
                    int height = 200;
                    int yOffset = 95;

                    int[] xPoints = {
                            x = 190,
                            x + base / 2,
                            x - base / 2
                    };
                    int[] yPoints = {

                            y - height / 2 + yOffset,
                            y + height / 2 + yOffset,
                            y + height / 2 + yOffset
                    };

                    g.fillPolygon(xPoints, yPoints, 3);
                });
            }

        });


        squareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawShape((g, x, y) -> {
                    g.setColor(java.awt.Color.RED);
                    int side = 200;
                    g.fillRect(x, y, side, side);
                });
            }
        });
    }

    private void drawShape(IShapeGraphics shapeGraphics) {
        shapeCanvas.setShapeGraphics(shapeGraphics);
    }

    private void injectDependencies() {
        // Create instances of IShapeGraphics for circle, square, triangle
        IShapeGraphics circleGraphics = (g, x, y) -> {
            g.setColor(java.awt.Color.BLUE);
            int diameter = 200;
            g.fillOval(x, y, diameter, diameter);
        };

        IShapeGraphics squareGraphics = (g, x, y) -> {
            g.setColor(java.awt.Color.RED);
            int side = 200;
            g.fillRect(x, y, side, side);
        };

        IShapeGraphics triangleGraphics = (g, x, y) -> {
            g.setColor(java.awt.Color.GREEN);
            int[] xPoints = {x, x + 100, x - 100};
            int[] yPoints = {y - 100, y + 100, y + 100};
            g.fillPolygon(xPoints, yPoints, 3);
        };

        graphicsController = new GraphicsController(circleGraphics, squareGraphics, triangleGraphics);
    }
}

package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.GraphicsController;
import model.IShapeGraphics;

/**
 * ControlPanel class to manage the UI components and interactions.
 * Author: Freddy Tapia
 */

public class ControlPanel  extends JPanel {
    private GraphicsPanel drawingPanel;
    private GraphicsController graphicsController;

    public ControlPanel () {
        setLayout(new BorderLayout());
        injectDependencies();

        drawingPanel = new GraphicsPanel(graphicsController);

        JButton circleButton = new JButton("Draw Circle");
        JButton triangleButton = new JButton("Draw Triangle");
        JButton squareButton = new JButton("Draw Square");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(circleButton);
        buttonPanel.add(triangleButton);
        buttonPanel.add(squareButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, buttonPanel, drawingPanel);
        splitPane.setDividerLocation(50);
        splitPane.setEnabled(false);

        add(splitPane, BorderLayout.CENTER);

        circleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setShape("Circle");
            }
        });

        triangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setShape("Triangle");
            }
        });

        squareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setShape("Square");
            }
        });
    }

    private void injectDependencies() {
        IShapeGraphics circleForm = g -> {
            g.setColor(java.awt.Color.BLUE);
            int diameter = 250;
            int x = (drawingPanel.getWidth() - diameter) / 2;
            int y = (drawingPanel.getHeight() - diameter) / 2;
            g.fillOval(x, y, diameter, diameter);
        };

        IShapeGraphics squareForm = g -> {
            g.setColor(java.awt.Color.RED);
            int side = 200;
            int x = (drawingPanel.getWidth() - side) / 2;
            int y = (drawingPanel.getHeight() - side) / 2;
            g.fillRect(x, y, side, side);
        };

        IShapeGraphics triangleForm = g -> {
            g.setColor(java.awt.Color.GREEN);
            int base = 200;
            int height = 200;
            int centerX = drawingPanel.getWidth() / 2;
            int centerY = drawingPanel.getHeight() / 2;

            int[] xPoints = {
                    centerX,
                    centerX + base / 2,
                    centerX - base / 2
            };
            int[] yPoints = {
                    centerY - height / 2,
                    centerY + height / 2,
                    centerY + height / 2
            };
            g.fillPolygon(xPoints, yPoints, 3);
        };

        graphicsController = new GraphicsController(circleForm, squareForm, triangleForm);
    }
}

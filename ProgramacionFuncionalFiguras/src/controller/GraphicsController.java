package controller;

import model.IShapeGraphics;

import java.awt.*;

public class GraphicsController {
    private IShapeGraphics circleForm;
    private IShapeGraphics squareForm;
    private IShapeGraphics triangleForm;

    public GraphicsController(IShapeGraphics circleForm, IShapeGraphics squareForm, IShapeGraphics triangleForm) {
        this.circleForm = circleForm;
        this.squareForm = squareForm;
        this.triangleForm = triangleForm;
    }

    public void drawShape(String shape, Graphics g) {
        switch (shape) {
            case "Circle":
                if (circleForm != null) {
                    circleForm.draw(g);
                }
                break;
            case "Triangle":
                if (triangleForm != null) {
                    triangleForm.draw(g);
                }
                break;
            case "Square":
                if (squareForm != null) {
                    squareForm.draw(g);
                }
                break;
        }
    }
}

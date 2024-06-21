package controller;

import model.IShapeGraphics;

public class GraphicsController {
    private IShapeGraphics circleForm;
    private IShapeGraphics squareForm;
    private IShapeGraphics triangleForm;

    public GraphicsController(IShapeGraphics circleForm, IShapeGraphics squareForm, IShapeGraphics triangleForm) {
        this.circleForm = circleForm;
        this.squareForm = squareForm;
        this.triangleForm = triangleForm;
    }

    public void functionCircle(IShapeGraphics circle, java.awt.Graphics g) {
        circleForm.drawShape(g, 250, 250);
    }

    public void functionTriangle(IShapeGraphics triangle, java.awt.Graphics g) {
        triangleForm.drawShape(g, 200, 200);
    }

    public void functionSquare(IShapeGraphics square, java.awt.Graphics g) {
        squareForm.drawShape(g, 200, 200);
    }
}

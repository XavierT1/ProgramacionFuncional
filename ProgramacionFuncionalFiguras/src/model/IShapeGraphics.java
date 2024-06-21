package model;

import java.awt.Graphics;

@FunctionalInterface
public interface IShapeGraphics {
    void drawShape(Graphics g, int x, int y);
}

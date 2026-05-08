package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;

/**
 * Renders canvas guide lines on a drawing panel.
 * Encapsulates the rendering strategy (dotted lines, coordinates, etc.)
 * so DrawerFeature remains focused on feature orchestration.
 */
public class CanvasRenderer {

    private final DrawPanelController drawPanelController;

    public CanvasRenderer(DrawPanelController drawPanelController) {
        this.drawPanelController = drawPanelController;
    }

    /**
     * Draw guide lines for the given canvas shape.
     *
     * @param shape the canvas shape whose guide lines to draw.
     */
    public void drawCanvasGuide(CanvasShape shape) {
        if (shape == null || !shape.isValid()) {
            return;
        }

        for (CanvasLine line : shape.getGuideLines()) {
            drawGuideLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        }
    }

    /**
     * Draw a single guide line with dotted style.
     */
    private void drawGuideLine(int x1, int y1, int x2, int y2) {
        ILine guideLine = LineFactory.getDottedLine();
        guideLine.setStartCoordinates(x1, y1);
        guideLine.setEndCoordinates(x2, y2);
        drawPanelController.drawLine(guideLine);
    }
}


package edu.kis.powp.jobs2d.features;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.canvas.CanvasFormat;
import edu.kis.powp.jobs2d.canvas.PaperFormat;
import edu.kis.powp.jobs2d.canvas.TriangleFormat;

import java.awt.geom.PathIterator;

public class CanvasFeature {
    public static void setupCanvasPlugin(Application application) {
        application.addComponentMenu(CanvasFeature.class, "Canvas", 0);

        for (PaperFormat format : PaperFormat.values()) {
            application.addComponentMenuElement(CanvasFeature.class, format.getName(), event -> setCanvas(format));
        }

        for (TriangleFormat format : TriangleFormat.values()) {
            application.addComponentMenuElement(CanvasFeature.class, format.getName(), event -> setCanvas(format));
        }
    }

    private static void setCanvas(CanvasFormat format) {
        PathIterator segments = format.getShape().getPathIterator(null);
        double[] coordinates = new double[2];

        double startX = 0;
        double startY = 0;
        double currentX = 0;
        double currentY = 0;

        while (!segments.isDone()) {
            int segmentType = segments.currentSegment(coordinates);

            if (segmentType == PathIterator.SEG_MOVETO) {
                startX = coordinates[0];
                startY = coordinates[1];
                currentX = coordinates[0];
                currentY = coordinates[1];
            } else if (segmentType == PathIterator.SEG_LINETO) {
                drawLine(currentX, currentY, coordinates[0], coordinates[1]);
                currentX = coordinates[0];
                currentY = coordinates[1];
            } else {
                drawLine(currentX, currentY, startX, startY);
            }

            segments.next();
        }
    }

    private static void drawLine(double x0, double y0, double x1, double y1) {
        ILine line = LineFactory.getBasicLine();

        line.setStartCoordinates((int) x0, (int) y0);
        line.setEndCoordinates((int) x1, (int) y1);

        DrawerFeature.getDrawerController().drawLine(line);
    }
}

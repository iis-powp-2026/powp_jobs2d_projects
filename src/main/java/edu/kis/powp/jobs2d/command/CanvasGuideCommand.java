package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.canvas.CanvasLine;
import edu.kis.powp.jobs2d.features.canvas.CanvasShape;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;

/**
 * Command to draw canvas guide lines for the current canvas shape.
 * Encapsulates canvas boundary rendering as a reusable, recordable command.
 */
public class CanvasGuideCommand implements DriverCommand {

    private final CanvasShape shape;

    public CanvasGuideCommand(CanvasShape shape) {
        this.shape = shape;
    }

    @Override
    public void execute(Job2dDriver driver) {
        if (shape == null || !shape.isValid()) {
            return;
        }

        for (CanvasLine line : shape.getGuideLines()) {
            ILine guideLine = LineFactory.getDottedLine();
            guideLine.setStartCoordinates(line.getX1(), line.getY1());
            guideLine.setEndCoordinates(line.getX2(), line.getY2());
            driver.operateTo(line.getX1(), line.getY1());
            driver.operateTo(line.getX2(), line.getY2());
        }
    }
}


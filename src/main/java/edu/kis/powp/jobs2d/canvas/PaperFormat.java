package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

public enum PaperFormat implements ICanvas {
    A4(new RectangleCanvas("A4", 210, 297, 0)),
    B3(new RectangleCanvas("B3", 353, 500, 0));

    private final RectangleCanvas canvas;

    PaperFormat(RectangleCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public boolean contains(int x, int y) {
        return canvas.contains(x, y);
    }

    @Override
    public int[] clip(int x1, int y1, int x2, int y2) {
        return canvas.clip(x1, y1, x2, y2);
    }

    @Override
    public ICompoundCommand toCommand() {
        return canvas.toCommand();
    }

    @Override
    public String getName() {
        return "Paper Format Canvas (" + name() + ")";
    }
}
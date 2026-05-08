package edu.kis.powp.jobs2d.features.canvas;

public enum PaperFormat {
    A4(210, 297),
    B3(353, 500);

    private final int width;
    private final int height;

    PaperFormat(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public CanvasShape toShape() {
        return new CustomShape(width, height);
    }
}


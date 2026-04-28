package edu.kis.powp.jobs2d.drivers.transformations;

public class ScaleTransformer implements CoordinateTransformer {
    private final double scaleX;
    private final double scaleY;

    public ScaleTransformer(double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public int[] transform(int x, int y) {
        return new int[]{ (int)(x * scaleX), (int)(y * scaleY) };
    }
}
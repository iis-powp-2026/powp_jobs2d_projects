package edu.kis.powp.jobs2d.drivers.transformations;

public class RotateTransformer implements CoordinateTransformer {
    private final double angleRadians;

    public RotateTransformer(double angleDegrees) {
        this.angleRadians = Math.toRadians(angleDegrees);
    }

    @Override
    public int[] transform(int x, int y) {
        int newX = (int) (x * Math.cos(angleRadians) - y * Math.sin(angleRadians));
        int newY = (int) (x * Math.sin(angleRadians) + y * Math.cos(angleRadians));
        return new int[]{ newX, newY };
    }
}
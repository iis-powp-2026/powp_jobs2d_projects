package edu.kis.powp.jobs2d.drivers.transformations;

public class FlipTransformer implements CoordinateTransformer {
    private final boolean flipX;
    private final boolean flipY;

    public FlipTransformer(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }

    @Override
    public int[] transform(int x, int y) {
        int newX = flipX ? -x : x;
        int newY = flipY ? -y : y;
        return new int[]{ newX, newY };
    }
}
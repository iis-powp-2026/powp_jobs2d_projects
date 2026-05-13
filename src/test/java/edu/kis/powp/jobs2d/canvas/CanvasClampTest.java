package edu.kis.powp.jobs2d.canvas;

public class CanvasClampTest {

    public static void main(String[] args) {
        testRectangleClamp();
        testCircleClamp();
        System.out.println("CanvasClampTest passed.");
    }

    private static void testRectangleClamp() {
        ICanvas canvas = new RectangleCanvas("test-rect", 100, 100, 0);
        int[] p = canvas.clampToBounds(200, 0);
        if (p[0] != 50 || p[1] != 0) {
            throw new AssertionError("Expected clamp to (50,0), got " + p[0] + "," + p[1]);
        }
        int[] inside = canvas.clampToBounds(10, -20);
        if (inside[0] != 10 || inside[1] != -20) {
            throw new AssertionError("Inside point must be unchanged");
        }
    }

    private static void testCircleClamp() {
        ICanvas canvas = new CircleCanvas("test-circle", 0, 0, 10, 0);
        int[] p = canvas.clampToBounds(100, 0);
        if (!canvas.contains(p[0], p[1])) {
            throw new AssertionError("Clamped point must be inside circle");
        }
        long dx = p[0];
        long dy = p[1];
        if (dx * dx + dy * dy > 100) {
            throw new AssertionError("Clamped point must be on or inside radius 10");
        }
    }
}

package edu.kis.powp.jobs2d.canvas;

import java.util.function.Supplier;

import edu.kis.powp.jobs2d.drivers.bounds.CanvasClampingDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class CanvasClampTest {

    public static void main(String[] args) {
        testRectangleClamp();
        testCircleClamp();
        testClampingDriverWithCanvas();
        testClampingDriverNullCanvas();
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

    private static void testClampingDriverWithCanvas() {
        ICanvas canvas = new RectangleCanvas("t", 20, 20, 0);
        CollectingDriver inner = new CollectingDriver();
        Supplier<ICanvas> supplier = () -> canvas;
        CanvasClampingDriver driver = new CanvasClampingDriver(inner, supplier, "bounded");
        driver.setPosition(100, 0);
        if (inner.lastSetX != 10 || inner.lastSetY != 0) {
            throw new AssertionError("Expected (10,0), got " + inner.lastSetX + "," + inner.lastSetY);
        }
        driver.operateTo(-100, 0);
        if (inner.lastOpX != -10 || inner.lastOpY != 0) {
            throw new AssertionError("Expected operateTo (-10,0)");
        }
    }

    private static void testClampingDriverNullCanvas() {
        CollectingDriver inner = new CollectingDriver();
        CanvasClampingDriver driver = new CanvasClampingDriver(inner, () -> null, "bounded-null");
        driver.setPosition(7, 8);
        if (inner.lastSetX != 7 || inner.lastSetY != 8) {
            throw new AssertionError("Without canvas, coordinates pass through");
        }
    }

    private static final class CollectingDriver implements VisitableDriver {
        int lastSetX;
        int lastSetY;
        int lastOpX;
        int lastOpY;

        @Override
        public void setPosition(int x, int y) {
            lastSetX = x;
            lastSetY = y;
        }

        @Override
        public void operateTo(int x, int y) {
            lastOpX = x;
            lastOpY = y;
        }

        @Override
        public void accept(DriverVisitor visitor) {
        }
    }
}

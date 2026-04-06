package edu.kis.powp.jobs2d.features;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.events.SelectClearPanelOptionListener;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.LineFactory;

public class DrawerFeature {

    public enum PaperFormat {
        A4(210, 297),
        B3(353, 500);

        private final int width;
        private final int height;

        PaperFormat(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public CustomShape toShape() {
            return new CustomShape(width, height);
        }
    }

    public static class CustomShape {
        private final int width;
        private final int height;

        public CustomShape(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public boolean isValid() {
            return width > 0 && height > 0;
        }
    }

    private static DrawPanelController drawerController;
    private static Application app;
    private static CustomShape currentCanvas = PaperFormat.A4.toShape();

    /**
     * Setup Drawer Plugin and add to application.
     *
     * @param application Application context.
     */
    public static void setupDrawerPlugin(Application application) {
        app = application;
        drawerController = new DrawPanelController();

        app.addComponentMenu(DrawPanelController.class, "Draw Panel", 0);
        app.addComponentMenuElement(DrawPanelController.class, "Clear Panel", new SelectClearPanelOptionListener());
        app.addComponentMenuElement(DrawPanelController.class, "Canvas A4", e -> setCanvas(PaperFormat.A4.toShape(), "A4"));
        app.addComponentMenuElement(DrawPanelController.class, "Canvas B3", e -> setCanvas(PaperFormat.B3.toShape(), "B3"));
        app.addComponentMenuElement(DrawPanelController.class, "Canvas Custom...", e -> selectCustomCanvas());

        drawerController.initialize(app.getFreePanel());
        redrawCanvasGuide();
        updateInfo("A4");
    }

    public static void clearPanel() {
        redrawCanvasGuide();
    }

    private static void setCanvas(CustomShape shape, String name) {
        if (shape == null || !shape.isValid()) {
            return;
        }

        currentCanvas = shape;
        redrawCanvasGuide();
        updateInfo(name);
    }

    private static void selectCustomCanvas() {
        JTextField widthField = new JTextField(Integer.toString(currentCanvas.getWidth()));
        JTextField heightField = new JTextField(Integer.toString(currentCanvas.getHeight()));

        Object[] message = { "Width:", widthField, "Height:", heightField };
        int result = JOptionPane.showConfirmDialog(null, message, "Custom Canvas", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int width = Integer.parseInt(widthField.getText().trim());
            int height = Integer.parseInt(heightField.getText().trim());
            setCanvas(new CustomShape(width, height), "Custom");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Width and height must be integer values.", "Invalid values",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void redrawCanvasGuide() {
        drawerController.clearPanel();

        int halfWidth = currentCanvas.getWidth() / 2;
        int halfHeight = currentCanvas.getHeight() / 2;

        drawLine(-halfWidth, -halfHeight, halfWidth, -halfHeight);
        drawLine(halfWidth, -halfHeight, halfWidth, halfHeight);
        drawLine(halfWidth, halfHeight, -halfWidth, halfHeight);
        drawLine(-halfWidth, halfHeight, -halfWidth, -halfHeight);
    }

    private static void drawLine(int x1, int y1, int x2, int y2) {
        ILine guideLine = LineFactory.getDottedLine();
        guideLine.setStartCoordinates(x1, y1);
        guideLine.setEndCoordinates(x2, y2);
        drawerController.drawLine(guideLine);
    }

    private static void updateInfo(String canvasName) {
        app.updateInfo("Canvas: " + canvasName + " (" + currentCanvas.getWidth() + "x" + currentCanvas.getHeight() + ")");
    }

    /**
     * Get controller of application drawing panel.
     *
     * @return drawPanelController.
     */
    public static DrawPanelController getDrawerController() {
        return drawerController;
    }
}

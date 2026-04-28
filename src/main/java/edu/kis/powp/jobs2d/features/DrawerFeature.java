package edu.kis.powp.jobs2d.features;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.features.canvas.CanvasLine;
import edu.kis.powp.jobs2d.features.canvas.CanvasShape;
import edu.kis.powp.jobs2d.features.canvas.CanvasRenderer;
import edu.kis.powp.jobs2d.features.canvas.CustomShape;
import edu.kis.powp.jobs2d.features.canvas.PaperFormat;
import edu.kis.powp.jobs2d.canvas.PaperFormat;
import edu.kis.powp.jobs2d.events.SelectClearPanelOptionListener;
import edu.kis.legacy.drawer.panel.DrawPanelController;

public class DrawerFeature implements IFeature {


    private static DrawPanelController drawerController;
    private static Application app;
    private static CanvasShape currentCanvas = PaperFormat.A4.toShape();
    private static CanvasRenderer canvasRenderer;
    private static boolean showCanvas = true;

    @Override
    public void setup(Application application) {
        setupDrawerPlugin(application);
    }

    @Override
    public String getName() {
        return "Drawer";
    }

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
        app.addComponentMenuElement(DrawPanelController.class, "Toggle Canvas Guide", e -> {
            showCanvas = !showCanvas;
            redrawCanvasGuide();
            updateInfo(showCanvas ? "Canvas shown" : "Canvas hidden");
        });

        drawerController.initialize(app.getFreePanel());
        canvasRenderer = new CanvasRenderer(drawerController);
        redrawCanvasGuide();
        updateInfo("A4");
    }

    public static void clearPanel() {
        redrawCanvasGuide();
    }

    private static void setCanvas(CanvasShape shape, String name) {
        if (shape == null || !shape.isValid()) {
            return;
        }

        currentCanvas = shape;
        redrawCanvasGuide();
        updateInfo(name);
    }

    private static void selectCustomCanvas() {
        int initialWidth = 210;
        int initialHeight = 297;
        if (currentCanvas instanceof CustomShape) {
            CustomShape rectangularShape = (CustomShape) currentCanvas;
            initialWidth = rectangularShape.getWidth();
            initialHeight = rectangularShape.getHeight();
        }

        JTextField widthField = new JTextField(Integer.toString(initialWidth));
        JTextField heightField = new JTextField(Integer.toString(initialHeight));

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

        if (!showCanvas) {
            return;
        }

        canvasRenderer.drawCanvasGuide(currentCanvas);
    }

    private static void updateInfo(String canvasName) {
        app.updateInfo("Canvas: " + canvasName + " (" + currentCanvas.describe() + ")" + (showCanvas ? " [guide]" : " [no guide]"));
    }

    /**
     * Get the currently selected canvas shape.
     *
     * @return the current canvas shape.
     */
    public static CanvasShape getCurrentCanvas() {
        return currentCanvas;
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

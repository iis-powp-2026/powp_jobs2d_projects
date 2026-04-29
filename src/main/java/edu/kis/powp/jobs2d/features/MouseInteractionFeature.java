package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;


/**
 * Feature responsible for translating mouse clicks into driver operations.
 * Left mouse button draws a line to the clicked position and moves the device head.
 * Right mouse button moves the device head without drawing.
 */
public class MouseInteractionFeature implements IFeature {
    /**
     * Ensures the mouse listener is registered only once.
     */
    private static boolean initialized = false;

    /**
     * Initializes mouse interaction for the application.
     * @param application the application context
     */
    @Override
    public void setup(Application application) {
        if (initialized) {
            return;
        }
        initialized = true;

        DriverManager driverManager = DriverFeature.getDriverManager();
        JPanel panel = application.getFreePanel();

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (driverManager.getCurrentDriver() == null) {
                    return;
                }

                int x = e.getX() - panel.getWidth() / 2;
                int y = e.getY() - panel.getHeight() / 2;

                switch (e.getButton()) {
                    case MouseEvent.BUTTON1: {
                        driverManager.getCurrentDriver().operateTo(x, y);
                        break;
                    }

                    case MouseEvent.BUTTON3: {
                        driverManager.getCurrentDriver().setPosition(x, y);
                        break;
                    }

                    default:
                        break;
                }
            }
        });
    }

    /**
     * Returns the name of this feature.
     * @return feature name
     */
    @Override
    public String getName() {
        return "Mouse Interaction";
    }
}

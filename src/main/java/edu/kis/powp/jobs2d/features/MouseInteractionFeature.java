package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.DriverManager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class MouseInteractionFeature implements IFeature {
    private static boolean initialized = false;

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

    @Override
    public String getName() {
        return "Mouse Interaction";
    }
}

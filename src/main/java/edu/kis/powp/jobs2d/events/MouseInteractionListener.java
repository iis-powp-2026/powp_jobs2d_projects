package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.drivers.DriverManager;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInteractionListener extends MouseAdapter {

    private final DriverManager driverManager;
    private final JPanel panel;

    public MouseInteractionListener(DriverManager driverManager, JPanel panel) {
        this.driverManager = driverManager;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (driverManager.getCurrentDriver() == null) {
            return;
        }

        int x = e.getX() - panel.getWidth() / 2;
        int y = e.getY() - panel.getHeight() / 2;

        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                driverManager.getCurrentDriver().operateTo(x, y);
                break;

            case MouseEvent.BUTTON3:
                driverManager.getCurrentDriver().setPosition(x, y);
                break;

            default:
                break;
        }
    }
}
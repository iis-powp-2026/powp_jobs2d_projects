package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickDriver extends MouseAdapter {

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public ClickDriver(JPanel panel) {
        panel.addMouseListener(this);
    }

    private Point getClickPosition(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        int offsetX = event.getComponent().getWidth()/2;
        int offsetY = event.getComponent().getHeight()/2;

        return new Point(x - offsetX, y - offsetY);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        Point position = getClickPosition(event);
        int buttonPressed = event.getButton();

        handleDriver(position, buttonPressed);
    }

    private void handleDriver(Point position, int buttonPressed) {
        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();

        if(buttonPressed == MouseEvent.BUTTON1) {
            driver.operateTo(position.getX(), position.getY());
        }

        if (buttonPressed == MouseEvent.BUTTON3) {
            driver.setPosition(position.getX(), position.getY());
        }
    }

}



package edu.kis.powp.jobs2d.events;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectChangeVisibleOptionListener implements ActionListener {
    private final Window controlledWindow;

    public SelectChangeVisibleOptionListener(Window controlledWindow) {
        super();
        this.controlledWindow = controlledWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controlledWindow.setVisible(!controlledWindow.isVisible());
    }
}

package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.features.CanvasFeature;

public class SelectClearPanelOptionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        CanvasFeature.clearPanel();
    }
}

package edu.kis.powp.jobs2d.command.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class CommandPreviewWindow extends JFrame implements WindowComponent {

    private JPanel previewPanel;
    private DrawPanelController drawController;

    public CommandPreviewWindow(DrawPanelController drawController) {
        this.drawController = drawController;

        this.setTitle("Command Preview");
        this.setSize(400, 400);

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        content.add(previewPanel, BorderLayout.CENTER);

        drawController.initialize(previewPanel);
    }

    public DrawPanelController getDrawController() {
        return drawController;
    }

    public void clear() {
        drawController.clearPanel();
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        this.setVisible(!this.isVisible());
    }
}
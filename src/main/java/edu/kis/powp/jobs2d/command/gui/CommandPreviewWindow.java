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

public class CommandPreviewWindow extends JFrame implements WindowComponent {

    private JPanel previewPanel;
    private DrawPanelController previewDrawController;
    private Job2dDriver previewDriver;

    public CommandPreviewWindow() {
        this.setTitle("Command Preview");
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        content.add(previewPanel, BorderLayout.CENTER);

        previewDrawController = new DrawPanelController();
        previewDrawController.initialize(previewPanel);
    }

    public void updatePreview(DriverCommand command) {
        previewDrawController.clearPanel();
        if (command != null && previewDriver != null) {
            command.execute(previewDriver);
        }
    }

    public DrawPanelController getDrawPanelController() {
        return previewDrawController;
    }

    public void setPreviewDriver(Job2dDriver previewDriver) {
        this.previewDriver = previewDriver;
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }
}

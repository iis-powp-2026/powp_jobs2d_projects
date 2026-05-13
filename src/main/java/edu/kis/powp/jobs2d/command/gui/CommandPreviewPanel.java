package edu.kis.powp.jobs2d.command.gui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.CommandPreviewChangeObserver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class CommandPreviewPanel extends JFrame{
    CommandPreviewChangeObserver commandPreview;
    DrawPanelController drawPanelController;
    VisitableDriver driver;

    public CommandPreviewPanel(DrawPanelController drawPanelController, VisitableDriver previewDriver) {
        this.drawPanelController = drawPanelController;
        this.driver = previewDriver;
    }
    public void initialize(JPanel previewPanel,CommandManager commandManager){
        commandPreview = new CommandPreviewChangeObserver(drawPanelController,driver,commandManager);
        drawPanelController.initialize(previewPanel);
    }
}
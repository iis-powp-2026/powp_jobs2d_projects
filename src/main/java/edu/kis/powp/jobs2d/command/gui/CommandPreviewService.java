package edu.kis.powp.jobs2d.command.gui;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

public class CommandPreviewService implements ICommandPreview {

    private final DrawPanelController drawController;
    private final VisitableDriver driver;

    public CommandPreviewService(DrawPanelController drawController, VisitableDriver driver) {
        this.drawController = drawController;
        this.driver = driver;
    }

    @Override
    public void updatePreview(DriverCommand command) {
        drawController.clearPanel();

        if (command != null) {
            command.execute(driver);
        }
    }
}

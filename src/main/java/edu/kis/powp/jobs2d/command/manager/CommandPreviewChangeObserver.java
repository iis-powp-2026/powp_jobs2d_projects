package edu.kis.powp.jobs2d.command.manager;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;

public class CommandPreviewChangeObserver implements Subscriber {
    private final DrawPanelController drawPanelController;
    private final VisitableDriver previewDriver;
    private final CommandManager commandManager;

    public CommandPreviewChangeObserver(DrawPanelController controller, VisitableDriver driver, CommandManager manager) {
        this.drawPanelController = controller;
        this.previewDriver = driver;
        this.commandManager = manager;
    }

    @Override
    public void update(){
        DriverCommand driverCommand = this.commandManager.getCurrentCommand();
        this.drawPanelController.clearPanel();
        if (driverCommand != null) {
            driverCommand.execute(this.previewDriver);
        }
    }

    public String toString() {
        return "Command Preview Change Observer";
    }
}

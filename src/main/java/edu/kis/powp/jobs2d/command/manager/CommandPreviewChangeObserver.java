package edu.kis.powp.jobs2d.command.manager;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;
import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.features.CanvasFeature;

public class CommandPreviewChangeObserver implements Subscriber {
    private final DrawPanelController drawPanelController;
    private final VisitableDriver previewDriver;
    private final VisitableDriver previewCanvasDriver;
    private final CommandManager commandManager;

    public CommandPreviewChangeObserver(DrawPanelController controller, VisitableDriver driver, VisitableDriver previewCanvasDriver, CommandManager manager) {
        this.drawPanelController = controller;
        this.previewDriver = driver;
        this.previewCanvasDriver = previewCanvasDriver;
        this.commandManager = manager;
    }

    @Override
    public void update(){
        DriverCommand driverCommand = this.commandManager.getCurrentCommand();
        ICanvas canvas = CanvasFeature.getCanvas(); 
        this.drawPanelController.clearPanel();
        if (canvas != null) {
            canvas.toCommand().execute(this.previewCanvasDriver);
        }
        if (driverCommand != null) {
            driverCommand.execute(this.previewDriver);
        }
    }

    public String toString() {
        return "Command Preview Change Observer";
    }
}

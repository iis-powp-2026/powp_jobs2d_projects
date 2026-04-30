package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Subscriber;

public class CommandPreviewObserver implements Subscriber {

    private ICommandManager commandManager;
    private ICommandPreview commandPreviewWindow;

    public CommandPreviewObserver(ICommandManager commandManager, ICommandPreview commandPreviewWindow) {
        this.commandManager = commandManager;
        this.commandPreviewWindow = commandPreviewWindow;
    }

    @Override
    public void update() {
        DriverCommand command = commandManager.getCurrentCommand();
        commandPreviewWindow.updatePreview(command);
    }

    @Override
    public String toString() {
        return "Command Preview Observer";
    }
}
package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.observer.Subscriber;

public class CommandPreviewObserver implements Subscriber {

    private CommandManager commandManager;
    private CommandPreviewWindow commandPreviewWindow;

    public CommandPreviewObserver(CommandManager commandManager, CommandPreviewWindow commandPreviewWindow) {
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

package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.composite.CommandComposite;
import edu.kis.powp.jobs2d.command.manager.DriverCommandManager;

/**
 * Listener for loading composite command example.
 * Demonstrates the composite pattern with nested command structures.
 */
public class SelectLoadCompositeCommandOptionListener extends AbstractAction {
    private DriverCommandManager commandManager;

    /**
     * Create listener.
     *
     * @param commandManager driver command manager
     */
    public SelectLoadCompositeCommandOptionListener(DriverCommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Square
        CommandComposite squareComposite = new CommandComposite("Square");
        squareComposite.add(new SetPositionCommand(100, 100));
        squareComposite.add(new OperateToCommand(200, 100));
        squareComposite.add(new OperateToCommand(200, 200));
        squareComposite.add(new OperateToCommand(100, 200));
        squareComposite.add(new OperateToCommand(100, 100));

        commandManager.setCurrentCommand(squareComposite);
    }
}


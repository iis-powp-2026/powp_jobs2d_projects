package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.visitor.DeepCopyVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectDeepCopyCommandOptionListener implements ActionListener {

    private final DeepCopyVisitor visitor = new DeepCopyVisitor();

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();

        visitor.reset();
        command.accept(visitor);

        ImmutableCompoundCommand newCommand = visitor.getCommands();
        CommandManager manager = CommandsFeature.getDriverCommandManager();
        manager.setCurrentCommand(newCommand);
    }
}

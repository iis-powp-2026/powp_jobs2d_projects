package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.visitor.CommandCountingVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Runs the {@link CommandCountingVisitor} over the current command
 * loaded in the CommandManager and logs per-type counts. This is the
 * test-app integration of the visitor pattern.
 */
public class SelectCountCommandsOptionListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (command == null) {
            logger.info("No command loaded - nothing to count.");
            return;
        }

        CommandCountingVisitor visitor = new CommandCountingVisitor();
        command.accept(visitor);

        logger.info("Command counts for '"
                + CommandsFeature.getDriverCommandManager().getCurrentCommandString() + "': "
                + "SetPositionCommand=" + visitor.getSetPositionCount()
                + ", OperateToCommand=" + visitor.getOperateToCount()
                + ", CompoundCommand=" + visitor.getCompoundCount()
                + ", leaves=" + visitor.getLeafCount()
                + ", total=" + visitor.getTotalCount());
    }
}
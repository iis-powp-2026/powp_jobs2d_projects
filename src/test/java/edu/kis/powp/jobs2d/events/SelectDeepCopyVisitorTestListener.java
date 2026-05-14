package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.visitor.DeepCopyVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Runs a deep copy test using {@link DeepCopyVisitor} on the current command
 * loaded in the CommandManager and logs the result.
 */
public class SelectDeepCopyVisitorTestListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        if (command == null) {
            logger.info("No command loaded - nothing to test deep copy.");
            return;
        }

        try {
            // Test with the current command
            DeepCopyVisitor visitor = new DeepCopyVisitor();
            command.accept(visitor);
            DriverCommand copy = visitor.getDeepCopy();

            if (command == copy) {
                logger.severe("Deep copy test FAILED: Copy is the same object as original");
                return;
            }

            // For compound commands, check deeper
            if (command instanceof ICompoundCommand) {
                ICompoundCommand originalCompound = (ICompoundCommand) command;
                ICompoundCommand copyCompound = (ICompoundCommand) copy;

                // Count commands
                int origCount = 0;
                for (DriverCommand c : (Iterable<DriverCommand>) originalCompound::iterator) {
                    origCount++;
                }
                int copyCount = 0;
                for (DriverCommand c : (Iterable<DriverCommand>) copyCompound::iterator) {
                    copyCount++;
                }
                if (origCount != copyCount) {
                    logger.severe("Deep copy test FAILED: Copy has different number of commands");
                    return;
                }

                // Check if children are different objects
                Iterator<DriverCommand> origIter = originalCompound.iterator();
                Iterator<DriverCommand> copyIter = copyCompound.iterator();
                while (origIter.hasNext() && copyIter.hasNext()) {
                    DriverCommand origChild = origIter.next();
                    DriverCommand copyChild = copyIter.next();
                    if (origChild == copyChild) {
                        logger.severe("Deep copy test FAILED: Child commands are the same object");
                        return;
                    }
                }
            }

            logger.info("Deep copy visitor test PASSED for command: " + CommandsFeature.getDriverCommandManager().getCurrentCommandString());

        } catch (Exception ex) {
            logger.severe("Deep copy test FAILED with exception: " + ex.getMessage());
        }
    }
}
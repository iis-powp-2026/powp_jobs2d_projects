package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.comparator.ComplexCommandComparationVisitor;
import edu.kis.powp.jobs2d.command.comparator.ComplexCommandComparator;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.drivers.visitor.FullNameGetterVisitor;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SelectCompareCommandsListener implements ActionListener {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private ComplexCommandComparator comparator = null;

    public SelectCompareCommandsListener(ComplexCommandComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand curr = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        DriverCommand prev = CommandsFeature.getDriverCommandManager().getPreviousCommand();

        if (curr instanceof ICompoundCommand && prev instanceof ICompoundCommand) {
            boolean same = comparator.compare((ICompoundCommand) curr, (ICompoundCommand) prev);
            logger.info("Current command and previous command are " + (same ? "same" : "different"));
        } else {
            logger.info("Current or previous command is not compound. Cannot compare.");
        }


    }
}

package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.visitor.CommandTransformVisitor;
import edu.kis.powp.jobs2d.drivers.transformations.CoordinateTransformer;
import edu.kis.powp.jobs2d.features.CommandsFeature;

/**
 * Listener that runs a {@link CommandTransformVisitor} on the current
 * command and replaces it in the {@link CommandManager} with the
 * transformed result. Multiple instances can be registered with
 * different transformers (e.g. scale 2x, rotate 45 degrees, flip Y).
 */
public class SelectTransformCommandOptionListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final CommandTransformVisitor visitor;
    private final String transformationName;

    /**
     * @param transformer        the transformation to apply to the current
     *                           command; must not be null.
     * @param transformationName human-readable name of the transformation
     *                           used for logging (e.g. "Scale 2x").
     */
    public SelectTransformCommandOptionListener(CoordinateTransformer transformer, String transformationName) {
        this.visitor = new CommandTransformVisitor(transformer);
        this.transformationName = transformationName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CommandManager manager = CommandsFeature.getDriverCommandManager();
        DriverCommand current = manager.getCurrentCommand();
        if (current == null) {
            logger.info("No command loaded - nothing to transform.");
            return;
        }

        String beforeName = manager.getCurrentCommandString();

        current.accept(visitor);
        DriverCommand transformed = visitor.getTransformedCommand();

        if (transformed == null) {
            logger.warning("Transformation produced no result for command '" + beforeName + "'.");
            return;
        }

        manager.setCurrentCommand(transformed);
        logger.info("Applied transformation '" + transformationName
                + "' to command '" + beforeName + "'. New command: "
                + manager.getCurrentCommandString());
    }
}
package edu.kis.powp.jobs2d.command.io;

import edu.kis.powp.jobs2d.command.ICompoundCommand;

public interface CommandImporter {
    /**
     * Imports a set of commands from a given text representation.
     *
     * @param text The text containing command declarations (e.g., JSON, XML).
     * @return Parsed ICompoundCommand.
     */
    ICompoundCommand importCommands(String text);
}

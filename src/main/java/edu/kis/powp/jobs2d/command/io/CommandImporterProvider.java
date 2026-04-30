package edu.kis.powp.jobs2d.command.io;

public interface CommandImporterProvider {
    /**
     * Determines if this provider can handle the given text construct.
     * @param text content of the command file
     * @return true if the format is recognized by this provider, false otherwise
     */
    boolean match(String text);

    /**
     * Gets an instance of a CommandImporter associated with this provider.
     * @return CommandImporter able to parse the previously matched text
     */
    CommandImporter getImporter();
}

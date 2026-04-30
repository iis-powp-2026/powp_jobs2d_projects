package edu.kis.powp.jobs2d.command.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Facility responsible for resolving and returning the appropriate
 * CommandImporter instance based on file contents or extension.
 */
public class CommandImporterFactory {

    private static final List<CommandImporterProvider> providers = new ArrayList<>();

    /**
     * Registers a new provider into the factory's list of supported loaders.
     * 
     * @param provider the provider implementing matching algorithms and instance
     *                 provider
     */
    public static void registerProvider(CommandImporterProvider provider) {
        providers.add(provider);
    }

    /**
     * Given the text content, returns the correct CommandImporter.
     * 
     * @param text content of the command file
     * @return adequate CommandImporter strategy
     * @throws IllegalArgumentException if no importer matches the content format
     */
    public static CommandImporter getImporter(String text) {
        for (CommandImporterProvider provider : providers) {
            if (provider.match(text)) {
                return provider.getImporter();
            }
        }

        throw new IllegalArgumentException("TXT file format not recognized. Was expecting JSON.");
    }
}

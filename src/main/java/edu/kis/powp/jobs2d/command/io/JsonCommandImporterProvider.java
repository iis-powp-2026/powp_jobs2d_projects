package edu.kis.powp.jobs2d.command.io;

public class JsonCommandImporterProvider implements CommandImporterProvider {

    @Override
    public boolean match(String text) {
        String trimmedText = text.trim();
        return trimmedText.startsWith("[") || trimmedText.startsWith("{");
    }

    @Override
    public CommandImporter getImporter() {
        return new JsonCommandImporter();
    }
}

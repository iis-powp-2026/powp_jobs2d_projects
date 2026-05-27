package edu.kis.powp.jobs2d.command.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.io.CommandImporter;
import edu.kis.powp.jobs2d.command.io.CommandImporterFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.observer.Subscriber;
import edu.kis.legacy.drawer.panel.DrawPanelController;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private final CommandManager commandManager;

    private final JTextArea currentCommandField;

    private final JTextArea observerListField;

    private final JPanel previewPanel;

    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(CommandManager commandManager) {
        this.setTitle("Command Manager");
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;

        GridBagConstraints constraint = new GridBagConstraints();

        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.gridx = 0;
        constraint.weighty = 0;
        content.add(observerListField, constraint);
        updateObserverListField();

        currentCommandField = new JTextArea("");
        currentCommandField.setEditable(false);
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.gridx = 0;
        constraint.weighty = 0;
        content.add(currentCommandField, constraint);
        updateCurrentCommandField();

        previewPanel = new JPanel();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.gridx = 0;
        constraint.weighty = 1;
        content.add(previewPanel, constraint);

        JButton btnImportCommands = new JButton("Import command");
        btnImportCommands.addActionListener((ActionEvent e) -> this.importCommands());
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.gridx = 0;
        constraint.weighty = 0;
        content.add(btnImportCommands, constraint);

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.gridx = 0;
        constraint.weighty = 0;
        content.add(btnClearCommand, constraint);

        JButton btnClearObservers = new JButton("Delete observers");
        btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.gridx = 0;
        constraint.weighty = 0;
        content.add(btnClearObservers, constraint);
    }

    public void initializePreviewPanel(DrawPanelController drawPanelController) {
        drawPanelController.initialize(previewPanel);
    }

    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
    }

    public void deleteObservers() {
        commandManager.getChangePublisher().clearObservers();
        this.updateObserverListField();
    }

    private void importCommands() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select command file to import");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON & TXT files", "json", "txt");
        fileChooser.addChoosableFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToImport = fileChooser.getSelectedFile();
            try {
                String text = Files.readString(fileToImport.toPath());

                CommandImporter importer = CommandImporterFactory.getImporter(text);

                ICompoundCommand importedCommand = importer.importCommands(text);

                commandManager.setCurrentCommand(importedCommand);
            } catch (IOException ex) {
                System.err.println("Error reading the file: " + ex.getMessage());
            } catch (Exception ex) {
                System.err.println("Error parsing the file: " + ex.getMessage());
            }
        }
    }

    private void updateObserverListField() {
        List<Subscriber> commandChangeSubscribers = commandManager.getChangePublisher().getSubscribers();
        if (commandChangeSubscribers.isEmpty()) {
            observerListField.setText("No observers loaded");
            return;
        }

        StringBuilder observerListBuilder = new StringBuilder();
        for (Subscriber observer : commandChangeSubscribers) {
            observerListBuilder
                    .append(observer)
                    .append(System.lineSeparator());
        }

        observerListField.setText(observerListBuilder.toString());
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        updateObserverListField();
        this.setVisible(!this.isVisible());
    }

}

package edu.kis.powp.jobs2d.command.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.io.CommandImporter;
import edu.kis.powp.jobs2d.command.io.CommandImporterFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformations.CoordinateTransformer;
import edu.kis.powp.jobs2d.drivers.transformations.ScaleTransformer;
import edu.kis.powp.jobs2d.drivers.transformations.TransformingDriver;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private CommandManager commandManager;

    private JLabel currentCommandField;

    private String observerListString;
    private JTextArea observerListField;

    private VisitableDriver previewDriver;
    private DrawPanelController drawerController;

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

        GridBagConstraints c = new GridBagConstraints();

        JPanel previewPanel = new JPanel();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 1;
        c.weighty = 7;
        c.gridwidth = 1;
        content.add(previewPanel, c);   

        drawerController = new DrawPanelController();
        drawerController.initialize(previewPanel);
        VisitableDriver driver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        CoordinateTransformer scaleDown = new ScaleTransformer(0.4, 0.4);
        VisitableDriver scaledDownDriver = new TransformingDriver(driver, scaleDown, "Transform: Scaled 0.4x");
        this.previewDriver = scaledDownDriver;

        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1;
        c.gridwidth = 2;
        content.add(observerListField, c);
        updateObserverListField();

        currentCommandField = new JLabel("");
        currentCommandField.setVerticalAlignment(SwingConstants.CENTER);
        currentCommandField.setHorizontalAlignment(SwingConstants.CENTER);
        currentCommandField.setOpaque(true);
        currentCommandField.setBackground(Color.WHITE);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 7;
        c.gridwidth = 1;
        content.add(currentCommandField, c);
        updateCurrentCommandField();

        JButton btnImportCommands = new JButton("Import command");
        btnImportCommands.addActionListener((ActionEvent e) -> this.importCommands());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 1;
        c.gridwidth = 2;
        content.add(btnImportCommands, c);

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 1;
        c.gridwidth = 2;
        content.add(btnClearCommand, c);

        JButton btnClearObservers = new JButton("Delete observers");
        btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 1;
        c.gridwidth = 2;
        content.add(btnClearObservers, c);
    }

    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
    }

    public void updateCurrentCommandField() {
        if (commandManager.getCurrentCommand() != null) {
            this.drawerController.clearPanel();
            this.commandManager.getCurrentCommand().execute(this.previewDriver);
        }
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
        observerListString = "";
        List<Subscriber> commandChangeSubscribers = commandManager.getChangePublisher().getSubscribers();
        for (Subscriber observer : commandChangeSubscribers) {
            observerListString += observer.toString() + System.lineSeparator();
        }
        if (commandChangeSubscribers.isEmpty())
            observerListString = "No observers loaded";

        observerListField.setText(observerListString);
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        updateObserverListField();
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }

}

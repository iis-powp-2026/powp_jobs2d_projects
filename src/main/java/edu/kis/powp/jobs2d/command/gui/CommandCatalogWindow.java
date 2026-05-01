package edu.kis.powp.jobs2d.command.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.DriverCommandFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.observer.Subscriber;

public class CommandCatalogWindow extends JFrame implements WindowComponent, Subscriber {

    private static final long serialVersionUID = 1L;

    private CommandManager commandManager;

    private DefaultListModel<String> commandListModel;
    private JList<String> commandList;

    public CommandCatalogWindow(CommandManager commandManager) {
        this.setTitle("Command Catalog");
        this.setSize(400, 400);

        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;

        commandListModel = new DefaultListModel<>();
        commandList = new JList<>(commandListModel);

        GridBagConstraints c = new GridBagConstraints();

        JScrollPane scrollPane = new JScrollPane(commandList);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        content.add(scrollPane, c);

        JButton btnLoadCommand = new JButton("Load");
        btnLoadCommand.addActionListener((ActionEvent e) -> this.loadSelectedCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 1;
        content.add(btnLoadCommand, c);

        JButton btnAddCommand = new JButton("Add");
        btnAddCommand.addActionListener((ActionEvent e) -> this.addCurrentCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 2;
        content.add(btnAddCommand, c);

        DriverCommandFactory.getCommandCatalogChangePublisher().addSubscriber(this);

        updateCommandList();
    }

    private void updateCommandList() {
        commandListModel.clear();

        for (String commandName : DriverCommandFactory.getCommandNames()) {
            commandListModel.addElement(commandName);
        }
    }

    private void loadSelectedCommand() {
        String selectedCommandName = commandList.getSelectedValue();

        if (selectedCommandName == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select command from catalog first.",
                    "No command selected",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        DriverCommand command = DriverCommandFactory.getCommand(selectedCommandName);

        if (command == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cannot load command: " + selectedCommandName,
                    "Command loading error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        commandManager.setCurrentCommand(command);
    }

    private void addCurrentCommand() {
        DriverCommand currentCommand = commandManager.getCurrentCommand();

        if (currentCommand == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "There is no current command in Command Manager.",
                    "No command to add",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String defaultName = commandManager.getCurrentCommandString();

        if (defaultName == null || defaultName.trim().isEmpty()) {
            defaultName = currentCommand.toString();
        }

        String commandName = JOptionPane.showInputDialog(
                this,
                "Command name:",
                defaultName
        );

        if (commandName == null || commandName.trim().isEmpty()) {
            return;
        }

        commandName = commandName.trim();

        if (DriverCommandFactory.containsCommand(commandName)) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Command '" + commandName + "' already exists. Overwrite?",
                    "Command already exists",
                    JOptionPane.YES_NO_OPTION
            );

            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        DriverCommandFactory.registerCommand(commandName, currentCommand);
    }

    @Override
    public void update() {
        updateCommandList();
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }
}
package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public class ComplexCommandEditor extends JFrame {

    private final CommandManager commandManager;
    private final CompoundCommand workingCopy;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> commandList = new JList<>(listModel);
    private final JTextField xField = new JTextField();
    private final JTextField yField = new JTextField();

    public ComplexCommandEditor(CommandManager commandManager) {
        this.commandManager = commandManager;

        if (commandManager.getCurrentCommand() == null) {
            workingCopy = new CompoundCommand();
        } else {
            workingCopy = (CompoundCommand) commandManager.getCurrentCommand().deepCopy();
        }

        setTitle("Complex Command Editor");
        setSize(500, 400);
        setLayout(new BorderLayout());
        refreshList();

        add(new JScrollPane(commandList), BorderLayout.CENTER);
        JPanel topPanel = new JPanel(new GridLayout(2, 2));

        topPanel.add(new JLabel("X"));
        topPanel.add(xField);
        topPanel.add(new JLabel("Y"));
        topPanel.add(yField);

        add(topPanel, BorderLayout.NORTH);
        JPanel buttons = new JPanel(new GridLayout(2, 2));

        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton removeButton = new JButton("Remove");
        JButton applyButton = new JButton("Apply");

        buttons.add(upButton);
        buttons.add(downButton);
        buttons.add(removeButton);
        buttons.add(applyButton);

        add(buttons, BorderLayout.SOUTH);

        commandList.addListSelectionListener(e -> loadCoordinates());

        upButton.addActionListener(e -> moveUp());
        downButton.addActionListener(e -> moveDown());
        removeButton.addActionListener(e -> removeCommand());
        applyButton.addActionListener(e -> applyCoordinates());
    }

    private void refreshList() {
        listModel.clear();
        Iterator<DriverCommand> iterator = workingCopy.iterator();

        while (iterator.hasNext()) {
            DriverCommand command = iterator.next();
            listModel.addElement(commandToString(command));
        }
    }

    private String commandToString(DriverCommand command) {
        if (command instanceof SetPositionCommand) {
            SetPositionCommand c = (SetPositionCommand) command;
            return "SetPosition(" + c.getPosX() + ", " + c.getPosY() + ")";
        }
        if (command instanceof OperateToCommand) {
            OperateToCommand c = (OperateToCommand) command;
            return "OperateTo(" + c.getPosX() + ", " + c.getPosY() + ")";
        }
        return command.toString();
    }

    private void loadCoordinates() {
        int index = commandList.getSelectedIndex();
        if (index < 0) {
            return;
        }
        DriverCommand command = getCommands().get(index);

        if (command instanceof SetPositionCommand) {
            SetPositionCommand c = (SetPositionCommand) command;

            xField.setText(String.valueOf(c.getPosX()));
            yField.setText(String.valueOf(c.getPosY()));
        }

        if (command instanceof OperateToCommand) {
            OperateToCommand c = (OperateToCommand) command;
            xField.setText(String.valueOf(c.getPosX()));
            yField.setText(String.valueOf(c.getPosY()));
        }
    }

    private void applyCoordinates() {
        int index = commandList.getSelectedIndex();
        if (index < 0) {
            return;
        }

        int x;
        int y;

        try {
            x = Integer.parseInt(xField.getText());
            y = Integer.parseInt(yField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid coordinates");
            return;
        }

        DriverCommand oldCommand = getCommands().get(index);
        if (oldCommand instanceof SetPositionCommand) {
            getCommands().set(index, new SetPositionCommand(x, y));
        }
        if (oldCommand instanceof OperateToCommand) {
            getCommands().set(index, new OperateToCommand(x, y));
        }

        commandManager.setCurrentCommand(workingCopy);
        refreshList();
        commandList.setSelectedIndex(index);
    }

    private void removeCommand() {
        int index = commandList.getSelectedIndex();
        if (index < 0) {
            return;
        }

        getCommands().remove(index);
        commandManager.setCurrentCommand(workingCopy);
        refreshList();

        if (!listModel.isEmpty()) {
            if (index >= listModel.size()) {
                commandList.setSelectedIndex(listModel.size() - 1);
            } else {
                commandList.setSelectedIndex(index);
            }
        }
    }

    private void moveUp() {
        int index = commandList.getSelectedIndex();
        if (index <= 0) {
            return;
        }

        swap(index, index - 1);
        commandList.setSelectedIndex(index - 1);
    }

    private void moveDown() {
        int index = commandList.getSelectedIndex();
        if (index < 0 || index >= listModel.size() - 1) {
            return;
        }

        swap(index, index + 1);
        commandList.setSelectedIndex(index + 1);
    }

    private void swap(int i, int j) {
        List<DriverCommand> commands = getCommands();
        DriverCommand temp = commands.get(i);
        commands.set(i, commands.get(j));
        commands.set(j, temp);
        refreshList();
    }

    private List<DriverCommand> getCommands() {
        try {
            Field field = CompoundCommand.class.getDeclaredField("commands");
            field.setAccessible(true);
            return (List<DriverCommand>) field.get(workingCopy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

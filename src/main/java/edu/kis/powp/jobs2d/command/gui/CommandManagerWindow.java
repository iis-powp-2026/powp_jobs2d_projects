package edu.kis.powp.jobs2d.command.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.canvas.CircleCanvas;
import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.canvas.PaperFormat;
import edu.kis.powp.jobs2d.canvas.gui.CanvasPanel;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private static final long serialVersionUID = 9204679248304669948L;

    private CommandManager commandManager;

    private JTextArea currentCommandField;
    private String observerListString;
    private JTextArea observerListField;

    private CanvasPanel canvasPanel;
    private JComboBox<String> canvasSelector;

    public CommandManagerWindow(CommandManager commandManager) {
        this.setTitle("Command Manager");
        this.setSize(700, 700);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;

        // observer list
        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        c.weighty = 0.2;
        c.gridy = 0;
        content.add(observerListField, c);
        updateObserverListField();

        // current command (text)
        currentCommandField = new JTextArea("");
        currentCommandField.setEditable(false);
        c.weighty = 0.2;
        c.gridy = 1;
        content.add(currentCommandField, c);
        updateCurrentCommandField();

        // canvas selector
        c.weighty = 0.0;
        c.gridy = 2;
        content.add(new JLabel("Preview canvas:"), c);

        canvasSelector = new JComboBox<>(new String[]{"None", "A4", "B3", "Circle"});
        canvasSelector.setSelectedItem("A4");
        canvasSelector.addActionListener(e -> updateCanvasPanelCanvas());
        c.gridy = 3;
        content.add(canvasSelector, c);

        // canvas + command preview
        canvasPanel = new CanvasPanel();
        c.weighty = 1.0;
        c.gridy = 4;
        content.add(canvasPanel, c);
        updateCanvasPanelCanvas();
        updateCanvasPanelCommand();

        // buttons
        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        c.weighty = 0.0;
        c.gridy = 5;
        content.add(btnClearCommand, c);

        JButton btnClearObservers = new JButton("Delete observers");
        btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
        c.gridy = 6;
        content.add(btnClearObservers, c);
    }

    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
        updateCanvasPanelCommand();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
        updateCanvasPanelCommand();
    }

    private void updateCanvasPanelCommand() {
        if (canvasPanel != null) {
            canvasPanel.setCommand(commandManager.getCurrentCommand());
        }
    }

    private void updateCanvasPanelCanvas() {
        if (canvasPanel == null) return;
        String sel = (String) canvasSelector.getSelectedItem();
        ICanvas canvas = null;
        if ("A4".equals(sel))            canvas = PaperFormat.A4;
        else if ("B3".equals(sel))       canvas = PaperFormat.B3;
        else if ("Circle".equals(sel))   canvas = new CircleCanvas("Circle r=200", 0, 0, 200, 20);
        canvasPanel.setCanvas(canvas);
    }

    public void deleteObservers() {
        commandManager.getChangePublisher().clearObservers();
        this.updateObserverListField();
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
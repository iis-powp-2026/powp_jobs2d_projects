package edu.kis.powp.jobs2d.features.history;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.kis.powp.appbase.gui.WindowComponent;

public class HistoryWindow extends JFrame implements WindowComponent {

    private HistoryManager historyManager;
    private JTextArea historyField;

    private static final long serialVersionUID = 1L;

    public HistoryWindow(HistoryManager historyManager) {
        this.setTitle("Application History");
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.historyManager = historyManager;

        GridBagConstraints c = new GridBagConstraints();

        historyField = new JTextArea("");
        historyField.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyField);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(scrollPane, c);

        JButton btnClearHistory = new JButton("Clear history");
        btnClearHistory.addActionListener((ActionEvent e) -> this.clearHistory());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 0;
        content.add(btnClearHistory, c);
        
        updateHistoryField();
    }

    private void clearHistory() {
        historyManager.clearHistory();
    }

    public void updateHistoryField() {
        List<String> historyList = historyManager.getHistoryList();
        StringBuilder historyString = new StringBuilder();
        for (int i = 0; i < historyList.size(); i++) {
            historyString.append(i + 1).append(". ").append(historyList.get(i)).append(System.lineSeparator());
        }
        if (historyList.isEmpty()) {
            historyString.append("No history");
        }
        historyField.setText(historyString.toString());
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

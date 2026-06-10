package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.history.CommandsHistory;
import edu.kis.powp.jobs2d.command.history.HistoryRecord;

import javax.swing.*;
import java.awt.*;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

public class CommandsHistoryWindow extends JFrame implements WindowComponent {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private final CommandsHistory commandsHistory;
    private final JPanel listPanel;
    private final Consumer<DriverCommand> commandSetter;

    public CommandsHistoryWindow(CommandsHistory commandsHistory, Consumer<DriverCommand> commandSetter) {
        this.commandsHistory = commandsHistory;
        this.commandSetter = commandSetter;

        this.setTitle("Commands History");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshHistory() {
        listPanel.removeAll();

        List<HistoryRecord> history = commandsHistory.getHistory();

        for (int i = 0; i < history.size(); i++) {
            HistoryRecord record = history.get(i);

            JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            String date = FORMATTER.format(record.getDatetime());
            String commandName = record.getCommand().toString();
            JLabel label = new JLabel((i + 1) + ".  " + date + "  -  " + commandName);

            JButton useButton = new JButton("Use");
            final HistoryRecord selectedRecord = record;
            useButton.addActionListener(e -> {
                commandSetter.accept(selectedRecord.getCommand());
            });

            rowPanel.add(label, BorderLayout.CENTER);
            rowPanel.add(useButton, BorderLayout.EAST);

            listPanel.add(rowPanel);
            listPanel.add(new JSeparator());
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (!this.isVisible()) {
            refreshHistory();
        }
        this.setVisible(!this.isVisible());
    }
}
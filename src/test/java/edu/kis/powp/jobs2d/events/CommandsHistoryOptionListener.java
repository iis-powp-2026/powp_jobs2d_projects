package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.history.HistoryRecord;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class CommandsHistoryOptionListener implements ActionListener {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void actionPerformed(ActionEvent e) {
        List<HistoryRecord> history = CommandsFeature.getCommandsHistory().getHistory();

        if(history.isEmpty()) {
            logger.info("History is empty");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss.SSS"
        ).withZone(ZoneId.systemDefault());

        stringBuilder.append("Commands history:\n");

        for (int i = 0; i < history.size(); i++) {
            HistoryRecord r = history.get(i);

            stringBuilder
                .append("    ")
                .append(i + 1)
                .append(". ")
                .append(formatter.format(r.getDatetime()))
                .append("    ")
                .append(r.getCommand())
                .append("\n")
            ;
        };

        logger.info(stringBuilder.toString());
    }
}

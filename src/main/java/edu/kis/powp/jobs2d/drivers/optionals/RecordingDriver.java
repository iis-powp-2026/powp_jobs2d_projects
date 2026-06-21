package edu.kis.powp.jobs2d.drivers.optionals;

import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;


/**
 * Driver that records all calls as command objects.
 * Recording can be temporarily disabled (used during playback).
 */
public class RecordingDriver implements VisitableDriver {

    private final List<DriverCommand> recorded = new ArrayList<>();
    private boolean recordingEnabled = true;

    public RecordingDriver() {};

    /**
     * Enable or disable recording of subsequent driver calls.
     * When disabled, setPosition/operateTo will still delegate to the target
     * but won't add commands to recorded list.
     */
    public synchronized void setRecordingEnabled(boolean enabled) {
        this.recordingEnabled = enabled;
    }

    public synchronized boolean isRecordingEnabled() {
        return recordingEnabled;
    }

    public synchronized void clearRecorded() {
        recorded.clear();
    }

    public synchronized List<DriverCommand> getRecordedCommands() {
        return List.copyOf(recorded);
    }

    @Override
    public synchronized void setPosition(int x, int y) {
        if (recordingEnabled) {
            recorded.add(new SetPositionCommand(x, y));
        }
    }

    @Override
    public synchronized void operateTo(int x, int y) {
        if (recordingEnabled) {
            recorded.add(new OperateToCommand(x, y));
        }
    }

    @Override
    public synchronized String toString() {
        return "RecordingDriver";
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}

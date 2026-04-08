package edu.kis.powp.jobs2d.drivers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

/**
 * Decorator driver that records all calls as command objects.
 * Recording can be temporarily disabled (used during playback).
 */
public class RecordingDriver implements Job2dDriver {

    private Job2dDriver target;
    private final List<DriverCommand> recorded = new ArrayList<>();
    private boolean recordingEnabled = true;

    public RecordingDriver(Job2dDriver initialTarget) {
        this.target = initialTarget;
    }

    public synchronized void setTarget(Job2dDriver target) {
        this.target = target;
    }

    public synchronized Job2dDriver getTarget() {
        return target;
    }

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
        return Collections.unmodifiableList(new ArrayList<>(recorded));
    }

    @Override
    public synchronized void setPosition(int x, int y) {
        if (recordingEnabled) {
            recorded.add(new SetPositionCommand(x, y));
        }
        target.setPosition(x, y);
    }

    @Override
    public synchronized void operateTo(int x, int y) {
        if (recordingEnabled) {
            recorded.add(new OperateToCommand(x, y));
        }
        target.operateTo(x, y);
    }

    @Override
    public synchronized String toString() {
        return "RecordingDriver -> " + target;
    }
}

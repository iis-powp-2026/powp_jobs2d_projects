package edu.kis.powp.jobs2d.drivers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.VisitableDriver;

/**
 * Decorator driver that records all calls as command objects.
 * Recording can be temporarily disabled (used during playback).
 */
public class RecordingDriver implements VisitableDriver {

    private VisitableDriver target;
    private final List<DriverCommand> recorded = new ArrayList<>();
    private boolean recordingEnabled = true;

    public RecordingDriver(VisitableDriver initialTarget) {
        this.target = initialTarget;
    }

    public synchronized void setTarget(VisitableDriver target) {
        this.target = target;
    }

    public synchronized VisitableDriver getTarget() {
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

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}

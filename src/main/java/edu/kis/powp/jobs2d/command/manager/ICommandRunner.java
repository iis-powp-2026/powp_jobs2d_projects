package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;

public interface ICommandRunner {
    void run(DriverCommand command);
}
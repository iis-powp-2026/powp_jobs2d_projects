package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

public class ImmutableCompoundCommandFactory
{
    public static ImmutableCompoundCommand getRectangle(int topLeftX, int topLeftY, int width, int height) {
        List<DriverCommand> commands = new ArrayList<>();

        commands.add(new SetPositionCommand(topLeftX, topLeftY));
        commands.add(new OperateToCommand(topLeftX + width, topLeftY));
        commands.add(new OperateToCommand(topLeftX + width, topLeftY - height));
        commands.add(new OperateToCommand(topLeftX, topLeftY - height));
        commands.add(new OperateToCommand(topLeftX, topLeftY));

        return new ImmutableCompoundCommand("Immutable Rectangle", commands);
    }
}
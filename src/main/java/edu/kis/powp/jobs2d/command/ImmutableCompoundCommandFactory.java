package edu.kis.powp.jobs2d.command;


public class ImmutableCompoundCommandFactory
{
    public static ImmutableCompoundCommand getRectangle(int topLeftX, int topLeftY, int width, int height) {
        SimpleComplexCommandBuilder builder = new SimpleComplexCommandBuilder();
        return builder
                .setPosition(topLeftX, topLeftY)
                .operateTo(topLeftX + width, topLeftY)
                .operateTo(topLeftX + width, topLeftY - height)
                .operateTo(topLeftX, topLeftY - height)
                .operateTo(topLeftX, topLeftY)
                .name("Immutable Rectangle")
                .buildImmutable();
    }
}
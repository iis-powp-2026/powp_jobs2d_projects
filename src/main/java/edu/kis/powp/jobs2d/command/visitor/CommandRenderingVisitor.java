package edu.kis.powp.jobs2d.command.visitor;

import java.awt.Color;
import java.awt.Graphics2D;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

/**
 * Visitor that renders a driver command onto a Graphics2D context.
 * Tracks the "pen" position between SetPosition (move without drawing)
 * and OperateTo (draw a line from current to target).
 */
public class CommandRenderingVisitor implements ICommandVisitor {

    private final Graphics2D g;
    private final ICanvas canvas; // may be null

    private int currentX = 0;
    private int currentY = 0;

    public CommandRenderingVisitor(Graphics2D g, ICanvas canvas) {
        this.g = g;
        this.canvas = canvas;
    }

    @Override
    public void visit(SetPositionCommand command) {
        // SetPosition moves the pen without drawing
        currentX = command.getPosX();
        currentY = command.getPosY();
    }

    @Override
    public void visit(OperateToCommand command) {
        int targetX = command.getPosX();
        int targetY = command.getPosY();

        Color old = g.getColor();
        if (canvas != null
                && (!canvas.contains(currentX, currentY) || !canvas.contains(targetX, targetY))) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLUE);
        }
        g.drawLine(currentX, currentY, targetX, targetY);
        g.setColor(old);

        currentX = targetX;
        currentY = targetY;
    }

    @Override
    public void visit(ICompoundCommand command) {
        for (DriverCommand child : (Iterable<DriverCommand>) command::iterator) {
            child.accept(this);
        }
    }
}
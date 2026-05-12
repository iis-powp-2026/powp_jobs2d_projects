package edu.kis.powp.jobs2d.canvas.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import edu.kis.powp.jobs2d.canvas.ICanvas;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.visitor.CommandRenderingVisitor;

/**
 * Swing panel that renders the outline of a selected canvas
 * together with a preview of the currently loaded driver command.
 *
 * The canvas and the command can be set independently;
 * each setter triggers a repaint.
 */
public class CanvasPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private ICanvas canvas;
    private DriverCommand command;

    public CanvasPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 500));
    }

    public void setCanvas(ICanvas canvas) {
        this.canvas = canvas;
        repaint();
    }

    public void setCommand(DriverCommand command) {
        this.command = command;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics gOriginal) {
        super.paintComponent(gOriginal);
        Graphics2D g = (Graphics2D) gOriginal.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // Translate origin to the center of the panel - canvases use
            // coordinates around (0,0), positive going right/down.
            g.translate(getWidth() / 2, getHeight() / 2);

            if (canvas != null) {
                canvas.draw(g);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.drawString("No canvas selected", -50, -10);
            }

            if (command != null) {
                CommandRenderingVisitor renderer = new CommandRenderingVisitor(g, canvas);
                command.accept(renderer);
            } else {
                g.setColor(Color.LIGHT_GRAY);
                g.drawString("No command loaded", -50, 10);
            }
        } finally {
            g.dispose();
        }
    }
}
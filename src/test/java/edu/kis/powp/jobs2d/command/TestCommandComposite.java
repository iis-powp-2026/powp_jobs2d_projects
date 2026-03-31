package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.composite.CommandComposite;

/**
 * Test for composite pattern.
 */
public class TestCommandComposite {

    /**
     * Test composite pattern with commands.
     */
    public static void main(String[] args) {
        Job2dDriver testDriver = new StubDriver();

        CommandComposite composite = new CommandComposite("DrawingSequence");

        composite.add(new SetPositionCommand(0, 0));
        composite.add(new OperateToCommand(100, 100));
        composite.add(new OperateToCommand(100, -100));
        composite.add(new OperateToCommand(-100, -100));
        composite.add(new OperateToCommand(-100, 100));
        composite.add(new OperateToCommand(0, 0));

        System.out.println("Composite: " + composite);
        System.out.println("Commands in composite: " + composite.size());
        System.out.println("Executing composite command...\n");

        composite.execute(testDriver);

        System.out.println("\n--- Testing nested composites ---\n");
        CommandComposite mainComposite = new CommandComposite("MainSequence");
        CommandComposite subComposite1 = new CommandComposite("SquareSequence");
        CommandComposite subComposite2 = new CommandComposite("ReturnSequence");

        subComposite1.add(new SetPositionCommand(50, 50));
        subComposite1.add(new OperateToCommand(150, 50));
        subComposite1.add(new OperateToCommand(150, 150));
        subComposite1.add(new OperateToCommand(50, 150));
        subComposite1.add(new OperateToCommand(50, 50));

        subComposite2.add(new OperateToCommand(0, 0));

        mainComposite.add(subComposite1);
        mainComposite.add(subComposite2);

        System.out.println("Main composite: " + mainComposite);
        System.out.println("Executing nested composites...\n");
        mainComposite.execute(testDriver);
    }

    /**
     * Stub driver for testing.
     */
    private static class StubDriver implements Job2dDriver {
        private int currentX = 0;
        private int currentY = 0;

        @Override
        public void operateTo(int x, int y) {
            System.out.println("operateTo(" + x + ", " + y + ") - from (" + currentX + ", " + currentY + ")");
            this.currentX = x;
            this.currentY = y;
        }

        @Override
        public void setPosition(int x, int y) {
            System.out.println("setPosition(" + x + ", " + y + ")");
            this.currentX = x;
            this.currentY = y;
        }
    }
}


package edu.kis.powp.jobs2d.command.composite;

/**
 * Simple useable composite pattern in driver hierarchy.
 */
public class CommandComposite extends AbstractCommandComposite {

    private String name;

    /**
     * Create composite with name.
     *
     * @param name composite name
     */
    public CommandComposite(String name) {
        super();
        this.name = name;
    }

    /**
     * Get composite name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set composite name.
     *
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CommandComposite[" + name + "] with " + commands.size() + " commands";
    }

}


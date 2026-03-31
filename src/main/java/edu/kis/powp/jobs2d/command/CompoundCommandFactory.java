package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating CompoundCommand instances.
 * Provides convenient methods for creating named compound commands.
 */
public class CompoundCommandFactory {

	/**
	 * Create TopSecretCommand with all its predefined commands.
	 * This is the sequence from SelectLoadSecretCommandOptionListener.
	 *
	 * @return TopSecretCommand as CompoundCommand
	 */
	public static CompoundCommand createTopSecretCommand() {
		List<DriverCommand> commands = new ArrayList<>();
		commands.add(new SetPositionCommand(-20, -50));
		commands.add(new OperateToCommand(-20, -50));
		commands.add(new SetPositionCommand(-20, -40));
		commands.add(new OperateToCommand(-20, 50));
		commands.add(new SetPositionCommand(0, -50));
		commands.add(new OperateToCommand(0, -50));
		commands.add(new SetPositionCommand(0, -40));
		commands.add(new OperateToCommand(0, 50));
		commands.add(new SetPositionCommand(70, -50));
		commands.add(new OperateToCommand(20, -50));
		commands.add(new OperateToCommand(20, 0));
		commands.add(new OperateToCommand(70, 0));
		commands.add(new OperateToCommand(70, 50));
		commands.add(new OperateToCommand(20, 50));

		return new CompoundCommand(commands, "TopSecretCommand");
	}

	/**
	 * Create an empty CompoundCommand.
	 *
	 * @return new empty CompoundCommand
	 */
	public static CompoundCommand createEmpty() {
		return new CompoundCommand();
	}

	/**
	 * Create a CompoundCommand with initial commands.
	 *
	 * @param commands initial list of commands
	 * @return new CompoundCommand with commands
	 */
	public static CompoundCommand create(List<DriverCommand> commands) {
		return new CompoundCommand(commands);
	}

	/**
	 * Create a named CompoundCommand with initial commands.
	 *
	 * @param commands initial list of commands
	 * @param name     name of the command
	 * @return new named CompoundCommand
	 */
	public static CompoundCommand create(List<DriverCommand> commands, String name) {
		return new CompoundCommand(commands, name);
	}

	/**
	 * Create a named CompoundCommand with variable arguments of commands.
	 *
	 * @param name     name of the command
	 * @param commands variable number of commands
	 * @return new named CompoundCommand
	 */
	public static CompoundCommand create(String name, DriverCommand... commands) {
		List<DriverCommand> commandList = new ArrayList<>();
		for (DriverCommand cmd : commands) {
			commandList.add(cmd);
		}
		return new CompoundCommand(commandList, name);
	}

	/**
	 * Create a named CompoundCommand with variable arguments of commands
	 * (varargs placed after name).
	 *
	 * @param commands variable number of commands
	 * @return new CompoundCommand
	 */
	public static CompoundCommand create(DriverCommand... commands) {
		List<DriverCommand> commandList = new ArrayList<>();
		for (DriverCommand cmd : commands) {
			commandList.add(cmd);
		}
		return new CompoundCommand(commandList);
	}
}

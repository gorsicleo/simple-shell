package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models exit command. Exit command terminates shell.
 * 
 * @author gorsicleo
 *
 */
public class ExitCommand implements ShellCommand {

	private static final String BYE_MESSAGE = "Shell terminated.\r\n";
	private static final String COMMAND_NAME = "exit";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Exits terminal", "\n\rcopy []\r");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return CommandUtil.terminateFatal(BYE_MESSAGE, env);
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}

}

package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CatCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "cat";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Prints the content of a file onto the standard output stream", 
			"cat [path] [charset]\r\n", 
			"\r\r path - Path to file.",
			"\r\r charset - if not provided default charset will be used.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		// TODO Auto-generated method stub
		return null;
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

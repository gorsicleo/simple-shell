package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CopyCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "copy";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Copies one or more files to another location.", 
			"copy [source] [destination]", 
			"\r\r source - Path to source file that needs to be copied.",
			"\r\r destination - Path to destination where file will be copied");

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

package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class TreeCommand implements ShellCommand {

	private static final String COMMAND_NAME = "tree";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Graphically displays the folder structure of a drive or path.", 
			"tree [directory] ", 
			"\r\r directory - directory name to print tree.");
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

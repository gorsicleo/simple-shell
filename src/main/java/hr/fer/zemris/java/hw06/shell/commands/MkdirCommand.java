package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class MkdirCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "mkdir";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Creates a directory.\r\n", 
			"\r\n mkdir [directory-name]\n",
			"\t directory-name is name of new directory."
			);

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
		// TODO Auto-generated method stub
		return COMMAND_DESCRIPTION;
	}

}

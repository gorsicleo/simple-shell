package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HexdumpCommand implements ShellCommand {

	private static final String COMMAND_NAME = "hexdump";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Display hex-output in a specified format", 
			"hexdump [path] ", 
			"\r\r path - Path to source file.");
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

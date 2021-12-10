package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class SymbolCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "symbol";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Command for changing symbols", 
			"symbol [symbol-type] [new-symbol] ", 
			"\r\r symbol-type - Type of symbol to be changed [PROMPT | MORELINES | MULTILINE]",
			"\r\r new-symbol - New symbol [# | % | $ | ...]");

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

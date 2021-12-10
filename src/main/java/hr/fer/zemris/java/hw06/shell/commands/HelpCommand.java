package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class HelpCommand implements ShellCommand {
	
	private List<Token> tokens;
	private String commandName;
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Provides help information for commands.\r\n", 
			"help [command]\r\n", 
			"\r\r command - displays help information on that command.",
			"\t command without arguments briefly lists all available commands.",
			"\t command with one argument displays command description for command name provided in argument.",
			"\t command with more than one argument is not valid.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();
		
		if (tokens.size() != 1) {
			env.writeln("Invalid number of arguments for command: "+getCommandName());
			return ShellStatus.CONTINUE;
		}
		
		if (tokens.get(0).getValue().isBlank()) {
			env.writeln("List of supported commands: ");
			env.commands().entrySet().stream().forEach(e -> env.writeln(e.getKey()));
		} else {
			commandName = tokens.get(0).getValue();
			if (env.commands().containsKey(commandName)) {
				env.writeln("Command: "+commandName);
				env.commands().
				get(commandName).
				getCommandDescription().
				stream().
				forEach(env::writeln);
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
				
	}

}

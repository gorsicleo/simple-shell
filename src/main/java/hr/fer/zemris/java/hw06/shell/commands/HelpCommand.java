package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;
import hr.fer.zemris.java.hw06.shell.ShellParser.TokenType;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Models help command. Help command provides help information for commands.
 * 
 * @author gorsicleo
 *
 */
public class HelpCommand implements ShellCommand {

	private static final String COMMAND_NAME_NOT_FOUND_ERROR = "Command name not found";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for command: ";
	private static final String LIST_OF_COMMANDS_MESSAGE = "List of supported commands: ";
	private static final String COMMAND_NAME = "help";
	private List<Token> tokens;
	private String commandName;
	private static final List<String> COMMAND_DESCRIPTION = List.of("Provides help information for commands.\r\n",
			"help [command]\r\n", "\r\r command - displays help information on that command.",
			"\t command without arguments briefly lists all available commands.",
			"\t command with one argument displays command description for command name provided in argument.",
			"\t command with more than one argument is not valid.");

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			return CommandUtil.terminateNotFatal(INVALID_NUMBER_OF_ARGS + getCommandName(), env);
		}

		if (tokens.get(0).getType().equals(TokenType.EOF)) {
			env.writeln(LIST_OF_COMMANDS_MESSAGE);
			env.commands().entrySet().stream().forEach(e -> env.writeln(e.getKey()));
		} else {
			commandName = tokens.get(0).getValue();
			if (env.commands().containsKey(commandName)) {
				env.writeln("Command: " + commandName);
				env.commands().get(commandName).getCommandDescription().stream().forEach(env::writeln);
			} else {
				CommandUtil.terminateNotFatal(COMMAND_NAME_NOT_FOUND_ERROR, env);
			}
		}
		return ShellStatus.CONTINUE;
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

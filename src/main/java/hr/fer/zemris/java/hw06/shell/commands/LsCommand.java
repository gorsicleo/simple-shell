package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**
 * Class that models ls command. Ls command lists all files in directory
 * 
 * @author gorsicleo
 *
 */
public class LsCommand implements ShellCommand {

	private static final String ATTRS_ERROR_MESSAGE = "Error occured when getting file attributes.";
	private static final String DIRECTORY_ERROR_MESSAGE = "Given path is not directory!";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for method symbol should be max 1, but was: ";
	private static final String COMMAND_NAME = "ls";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Formatted output of directory listing.\n\r",
			"ls [directory] ", "\r\r directory - Directory path to write listing");

	/** List of tokens that represent command arguments */
	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			return CommandUtil.terminateNotFatal(INVALID_NUMBER_OF_ARGS + tokens.size(), env);
		}

		File folder = new File(CommandUtil.removeQuotation(tokens.get(0).getValue()));

		if (!folder.isDirectory()) {
			return CommandUtil.terminateNotFatal(DIRECTORY_ERROR_MESSAGE, env);
		}

		try {
			env.write(CommandUtil.printFileAttrs(folder));
		} catch (IOException e) {
			return CommandUtil.terminateNotFatal(ATTRS_ERROR_MESSAGE, env);
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

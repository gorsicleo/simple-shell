package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**
 * Models hexdump command. Hexdump command displays hex-output in a specified
 * format
 * 
 * @author gorsicleo
 *
 */
public class HexdumpCommand implements ShellCommand {

	private static final String NON_FATAL_ERROR_MESSAGE = "Error occured while opening file.";
	private static final String FATAL_ERROR_MESSAGE = "Fatal error.";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for method symbol should be 1, but was: ";
	private static final String DIRECTORY_ERROR_MESSAGE = "You must provide path to file, not directory.";
	private static final String COMMAND_NAME = "hexdump";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Display hex-output in a specified format",
			"hexdump [path] ", "\r\r path - Path to source file.");

	/** List of tokens that represent command arguments */
	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			return CommandUtil.terminateNotFatal(INVALID_NUMBER_OF_ARGS + tokens.size(), env);
		}

		String path = CommandUtil.removeQuotation(tokens.get(0).getValue());

		File file = new File(path);

		if (file.isDirectory()) {
			return CommandUtil.terminateNotFatal(DIRECTORY_ERROR_MESSAGE, env);
		}

		try {
			env.write(CommandUtil.convertFileToHexDump(file.toPath()));
		} catch (ShellIOException e) {
			return CommandUtil.terminateFatal(FATAL_ERROR_MESSAGE, env);
		} catch (IOException e) {
			return CommandUtil.terminateNotFatal(NON_FATAL_ERROR_MESSAGE, env);
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

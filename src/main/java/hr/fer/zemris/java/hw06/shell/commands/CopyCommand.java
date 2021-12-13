package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**
 * Class that models copy command. Command copy copies one or more files to
 * another location.
 * 
 * @author gorsicleo
 *
 */
public class CopyCommand implements ShellCommand {

	private static final String COPY_ERROR_MESSAGE = "Error occured while copying file.";
	private static final String COPY_DONE_MESSAGE = "Copying done.";
	private static final String OPERATION_CANCELLED_MESSAGE = "Operation cancelled.";
	private static final String FILE_ALREADY_EXISTS_MESSAGE = "File already exists. Overwrite? [y/n]";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for method symbol should be 2, but was: ";
	private static final String COMMAND_NAME = "copy";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Copies one or more files to another location.",
			"copy [source] [destination]", "\r\r source - Path to source file that needs to be copied.",
			"\r\r destination - Path to destination where file will be copied");

	/** List of tokens that represent command arguments */
	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 2) {
			env.writeln(INVALID_NUMBER_OF_ARGS + tokens.size());
			return ShellStatus.CONTINUE;
		}

		File source = new File(CommandUtil.removeQuotation(tokens.get(0).getValue()));

		File destination = new File(CommandUtil.removeQuotation(tokens.get(1).getValue()));

		destination = destination.isDirectory() ? new File(destination.getPath() + '\\' + source.getName())
				: destination;

		if (destination.exists()) {
			env.writeln(FILE_ALREADY_EXISTS_MESSAGE);
			String isOverwrite = env.readLine();
			while (!isOverwrite.equalsIgnoreCase("y") && !isOverwrite.equalsIgnoreCase("n")) {
				env.writeln(FILE_ALREADY_EXISTS_MESSAGE);
				isOverwrite = env.readLine();
			}
			if (isOverwrite.equalsIgnoreCase("n")) {
				return CommandUtil.terminateNotFatal(OPERATION_CANCELLED_MESSAGE, env);
			}

		}

		try (InputStream in = new BufferedInputStream(new FileInputStream(source));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(destination))) {

			byte[] buffer = new byte[4096];
			int lengthRead;
			while ((lengthRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, lengthRead);
				out.flush();
			}
			env.writeln(COPY_DONE_MESSAGE);
		} catch (IOException e) {
			env.writeln(COPY_ERROR_MESSAGE);

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

package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**
 * Class that models cat command. Cat command prints the content of a file onto
 * the standard output stream.
 * 
 * @author gorsicleo
 *
 */
public class CatCommand implements ShellCommand {

	private static final String NON_FATAL_ERROR = "Error occured while opening file.";
	private static final String FATAL_ERROR = "Fatal error.";
	private static final String DIRECTORY_ERR = "Please check that given path is file and not directory!";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for method symbol should be max 2, but was: ";
	private static final String COMMAND_NAME = "cat";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Prints the content of a file onto the standard output stream", "cat [path] [charset]\r\n",
			"\r\r path - Path to file.", "\r\r charset - if not provided default charset will be used.");

	/** List of tokens that represent command arguments */
	private List<Token> tokens;

	// Small note: javadocs for these methods are inherited from implemented
	// interface

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() > 2) {
			return CommandUtil.terminateNotFatal(INVALID_NUMBER_OF_ARGS + tokens.size(), env);
		}

		try {
			env.write(tokens.size() == 2 ? handleCustomCharset(CommandUtil.removeQuotation(tokens.get(1).getValue()))
					: handleDefaultCharset());
		} catch (IllegalArgumentException e) {
			return CommandUtil.terminateNotFatal(DIRECTORY_ERR, env);
		} catch (ShellIOException e) {
			return CommandUtil.terminateFatal(FATAL_ERROR, env);
		} catch (IOException e) {
			return CommandUtil.terminateNotFatal(NON_FATAL_ERROR, env);
		}

		return ShellStatus.CONTINUE;
	}

	/** Calls method handleCustomCharset with default charset as argument */
	private String handleDefaultCharset() throws ShellIOException, IOException {
		return handleCustomCharset(Charset.defaultCharset().name());

	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}

	/**
	 * Method reads file with provided charset.
	 * 
	 * @param charset
	 * @return String that is read from file.
	 * @throws ShellIOException
	 * @throws IOException
	 */
	private String handleCustomCharset(String charset) throws ShellIOException, IOException {
		String text = "";
		String path = CommandUtil.removeQuotation(tokens.get(0).getValue());

		if (new File(path).isDirectory()) {
			throw new IllegalArgumentException();
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), charset));
		String line;
		while ((line = br.readLine()) != null) {
			text += line + "\n";
		}
		br.close();
		return text;

	}

}

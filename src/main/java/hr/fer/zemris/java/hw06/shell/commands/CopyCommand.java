package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class CopyCommand implements ShellCommand {

	private static final String COMMAND_NAME = "copy";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Copies one or more files to another location.",
			"copy [source] [destination]", "\r\r source - Path to source file that needs to be copied.",
			"\r\r destination - Path to destination where file will be copied");

	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 2) {
			env.writeln("Invalid number of arguments for method symbol should be 2, but was: " + tokens.size());
			return ShellStatus.CONTINUE;
		}

		File source = new File(tokens.get(0).getValue().startsWith("\"") ? tokens.get(0).getValue().replace("\"", "")
				: tokens.get(0).getValue());

		File destination = new File(
				tokens.get(1).getValue().startsWith("\"") ? tokens.get(1).getValue().replace("\"", "")
						: tokens.get(1).getValue());

		destination = destination.isDirectory() ? new File(destination.getPath() + '\\' + source.getName())
				: destination;

		if (destination.exists()) {
			env.writeln("File already exists. Overwrite? [y/n]");
			String isOverwrite = env.readLine();
			while (!isOverwrite.equalsIgnoreCase("y") && !isOverwrite.equalsIgnoreCase("n")) {
				env.writeln("File already exists. Overwrite? [y/n]");
				isOverwrite = env.readLine();
			}
			if (isOverwrite.equalsIgnoreCase("n")) {
				env.writeln("Operation cancelled.");
				return ShellStatus.CONTINUE;
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
			env.writeln("Copying done.");
		} catch (IOException e) {
			env.writeln("Error occured while copying file.");

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

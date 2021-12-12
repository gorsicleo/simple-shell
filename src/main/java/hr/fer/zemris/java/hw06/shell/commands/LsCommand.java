package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class LsCommand implements ShellCommand {

	private static final String COMMAND_NAME = "ls";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Formatted output of directory listing.\n\r",
			"ls [directory] ", "\r\r directory - Directory path to write listing");

	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			env.writeln("Invalid number of arguments for method symbol should be max 1, but was: " + tokens.size());
			return ShellStatus.CONTINUE;
		}

		File folder = new File(tokens.get(0).getValue().startsWith("\"") ? tokens.get(0).getValue().replace("\"", "")
				: tokens.get(0).getValue());

		if (!folder.isDirectory()) {
			env.writeln("Given path is not directory!");
			return ShellStatus.CONTINUE;
		}

		try {
			env.write(printFileAttrs(folder));
		} catch (IOException e) {
			env.writeln("Error occured when getting file attributes.");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	private String printFileAttrs(File folder) throws IOException {
		File[] objectsInDir = folder.listFiles();
		String allFilesAttrs = new String();
		for (File object : objectsInDir) {
			String permissions = "";
			permissions += object.isDirectory() ? "d" : "-";
			permissions += object.canRead() ? "r" : "-";
			permissions += object.canWrite() ? "w" : "-";
			permissions += object.canExecute() ? "x" : "-";
			long fileSize = object.length();
			String creationTime = Files.getAttribute(object.toPath(), "creationTime").toString().replace('T', ' ');
			String fileName = object.getName();
			allFilesAttrs += String.format("%-5s %7d %-20s %s \n", permissions, fileSize,
					creationTime.substring(0, 19), fileName);

		}
		return allFilesAttrs;
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

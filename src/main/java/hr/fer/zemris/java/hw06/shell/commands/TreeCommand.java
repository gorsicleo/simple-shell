package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**
 * Class that models TreeCOmmand. Tree command graphically displays the folder
 * structure of a drive or path.
 * 
 * @author gorsicleo
 */
public class TreeCommand implements ShellCommand {

	private static final String PERMISSIONS_ERROR_MESSAGE = "Problem occured when walking through tree, make sure you have required permissions to read all subdirectories.";
	private static final String DIRECTORY_ERROR_MESSAGE = "Given path is not direcotry!";
	private static final String DIRECTORY_FOUND = "[DIR] ";
	private static final String INVALID_NUMBER_OF_ARGS = "Invalid number of arguments for method symbol should be max 1, but was: ";
	private static final String COMMAND_NAME = "tree";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Graphically displays the folder structure of a drive or path.", "tree [directory] ",
			"\r\r directory - directory name to print tree.");

	/** List of tokens that represent command arguments */
	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			return CommandUtil.terminateNotFatal(INVALID_NUMBER_OF_ARGS + tokens.size(), env);
		}

		String path = CommandUtil.removeQuotation(tokens.get(0).getValue());

		File directory = new File(path);

		if (directory.isDirectory()) {
			try {
				env.write(renderFolder(new File(path), 0, new StringBuilder(), false).toString());
			} catch (NullPointerException e) {
				return CommandUtil.terminateNotFatal(PERMISSIONS_ERROR_MESSAGE, env);
			}

		} else {
			env.writeln(DIRECTORY_ERROR_MESSAGE);
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

	/**
	 * Method iterates through all files in directory and calls renderFolder and
	 * renderFile respectively
	 */
	private static StringBuilder renderFolder(File folder, int level, StringBuilder sb, boolean isLast) {
		indent(sb, level, isLast).append(DIRECTORY_FOUND + folder.getName() + "\n");

		File[] objectsInDir = folder.listFiles();

		for (int i = 0; i < objectsInDir.length; i++) {
			boolean last = ((i + 1) == objectsInDir.length);

			if (objectsInDir[i].isDirectory()) {
				renderFolder(objectsInDir[i], level + 1, sb, last);
			} else {
				renderFile(objectsInDir[i], level + 1, sb, last);
			}
		}

		return sb;
	}

	/** Method prints filename in formatted manner */
	private static StringBuilder renderFile(File file, int level, StringBuilder sb, boolean isLast) {
		return indent(sb, level, isLast).append("── " + file.getName() + "\n");
	}

	/** Method calculates and appends indent depending on current depth */
	private static StringBuilder indent(StringBuilder sb, int level, boolean isLast) {
		sb.append("|  ".repeat(level));

		if (level > 0) {
			sb.append(isLast ? "├─" : "├─");
		}

		return sb;
	}

}

package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class MkdirCommand implements ShellCommand {

	private static final String COMMAND_NAME = "mkdir";
	private static final List<String> COMMAND_DESCRIPTION = List.of("Creates a directory.\r\n",
			"\r\n mkdir [directory-name]\n", "\t directory-name is name of new directory.");

	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();

		if (tokens.size() != 1) {
			env.writeln("Invalid number of arguments for method symbol should be max 1, but was: " + tokens.size());
			return ShellStatus.CONTINUE;
		}

		String path = tokens.get(0).getValue().startsWith("\"") ? tokens.get(0).getValue().replace("\"", "")
				: tokens.get(0).getValue();

		if (new File(path).mkdirs()) {
			env.writeln("Directory created.");
		} else {
			env.writeln("Directory not created.");
		}

		return ShellStatus.CONTINUE;

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

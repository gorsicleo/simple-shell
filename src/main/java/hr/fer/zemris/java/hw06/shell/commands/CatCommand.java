package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class CatCommand implements ShellCommand {

	private static final String COMMAND_NAME = "cat";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Prints the content of a file onto the standard output stream", "cat [path] [charset]\r\n",
			"\r\r path - Path to file.", "\r\r charset - if not provided default charset will be used.");

	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();
		
		if (tokens.size() > 2) {
			env.writeln("Invalid number of arguments for method symbol should be max 2, but was: "+tokens.size());
			return ShellStatus.CONTINUE;
		}
		
		try {
			env.write(tokens.size() == 2? handleCustomCharset(tokens.get(1).getValue()): handleDefaultCharset());
		} catch (ShellIOException e) {
			env.writeln("Fatal error.");
			return ShellStatus.TERMINATE;
		} catch (IOException e) {
			env.writeln("Error occured while opening file.");
			return ShellStatus.CONTINUE;
		}

		
		return ShellStatus.CONTINUE;
	}

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

	private String handleCustomCharset(String charset) throws ShellIOException, IOException {
		String text = "";
		String path = tokens.get(0).getValue().startsWith("\"") ? tokens.get(0).getValue().replace("\"", "")
				: tokens.get(0).getValue();
		BufferedReader br = new BufferedReader(
		           new InputStreamReader(new FileInputStream(path), charset));
		String line;
		while ((line = br.readLine()) != null) {
			text += line+"\n";
		}
		br.close();
		return text;

	}

}

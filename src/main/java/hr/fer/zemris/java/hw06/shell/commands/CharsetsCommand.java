package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class CharsetsCommand implements ShellCommand {

	private static final String COMMAND_NAME = "charsets";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Lists names of supported charsets for Java platform", 
			"charsets []\r\n" );
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.writeln("Available charsets: ");
		Charset.availableCharsets()
		.entrySet()
		.stream()
		.map(Map.Entry::getKey)
		.forEach(key -> env.writeln(key));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}

}

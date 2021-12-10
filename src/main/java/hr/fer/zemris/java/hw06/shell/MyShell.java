package hr.fer.zemris.java.hw06.shell;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class MyShell {

	public static void main(String[] args) {
		EnvironmentImpl environment = new EnvironmentImpl();
		String userInput;
		String arguments;
		String commandName;
		ShellCommand command;
		List<Token> tokens;
		ShellStatus status;
		
		
		
		while (true) {
			userInput = environment.readLine();
			tokens = (new ShellParser(userInput,ParsingMode.USER_INPUT_PARSING)).getTokens();
			command = environment.commands().get(tokens.get(0).getValue());
			arguments = tokens.size()==2 ? tokens.get(1).getValue(): " ";
			status = command.executeCommand(environment,arguments );
		}
	}
}

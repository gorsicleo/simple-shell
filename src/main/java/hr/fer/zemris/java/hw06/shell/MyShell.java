package hr.fer.zemris.java.hw06.shell;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class MyShell {

	public static void main(String[] args) {
		EnvironmentImpl environment = new EnvironmentImpl();
		String userInput;
		String commandName;
		ShellCommand command;
		List<Token> tokens;
		
		
		
		while (true) {
			userInput = environment.readLine();
			tokens = (new ShellParser(userInput)).getTokens();
			command = environment.commands().get(tokens.get(0).getValue());
			System.out.println("You are asking command: "+command.getCommandName());
		}
	}
}

package hr.fer.zemris.java.hw06.shell;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

/**Demo program for demonstrating my shell.
 * Consists of single function (main) that creates and runs
 * shell.
 * @author gorsicleo
 *
 */
public class MyShell {

	private static final String BYE_MESSAGE = "Shell terminated.\r\n";
	private static final String WELCOME_MESSAGE = "Welcome to MyShell v 1.0\r\n";

	/**Method creates new environment and runs in loop until user terminates
	 * loop with exit command.
	 * @param takes no arguments
	 */
	public static void main(String[] args) {
		EnvironmentImpl environment = new EnvironmentImpl();
		String userInput;
		String arguments;
		ShellCommand command;
		List<Token> tokens;
		ShellStatus status = ShellStatus.CONTINUE;
		
		environment.writeln(WELCOME_MESSAGE);
		
		while (status==ShellStatus.CONTINUE) {
			
			userInput = environment.readLine();
			tokens = (new ShellParser(userInput,ParsingMode.USER_INPUT_PARSING)).getTokens();
			command = environment.commands().get(tokens.get(0).getValue());
			arguments = tokens.size()==2 ? tokens.get(1).getValue(): " ";
			status = command.executeCommand(environment,arguments );
		}
		
		environment.writeln(BYE_MESSAGE);
	}
}

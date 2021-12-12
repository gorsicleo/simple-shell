package hr.fer.zemris.java.hw06.shell;

/**Exception that is raised when lexer is unable to tokenize input properly
 * @author gorsicleo
 *
 */
@SuppressWarnings("serial")
public class ShellLexerException extends RuntimeException{

	public ShellLexerException(String message) {
		super(message);
	}
}

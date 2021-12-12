package hr.fer.zemris.java.hw06.shell;

/**Exception that occurs when errors occur during I/O operations on shell.
 * Shell should terminate if this exception raises.
 * @author gorsicleo
 *
 */
@SuppressWarnings("serial")
public class ShellIOException extends RuntimeException {

	public ShellIOException(String message) {
		super(message);
	}

	
}

package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**Model for single shell command.
 * Model that implements this interface must implement
 * command name, description, and execution.
 * @author gorsicleo
 *
 */
public interface ShellCommand {

	/**Method that executes particular command on given environment and with given arguments.
	 * @param env environment to execute command on.
	 * @param arguments for executing command.
	 * @return status of execution. 
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**Returns command name
	 * @return command name
	 */
	String getCommandName();
	
	/**returns short description and usage for this command
	 * @return list of strings representing command description
	 */
	List<String> getCommandDescription();
	
}

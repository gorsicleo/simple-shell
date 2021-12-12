package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**Abstraction for user environment. 
 * 
 * Every IO command will have to use methods from this interface.
 * @author gorsicleo
 *
 */
public interface Environment {

	/**Reads user input from shell.
	 * @return User input from shell
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;
	
	/**Writes text to shell.
	 * @param text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;
	
	/**Writes one line to shell.
	 * @param text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;
	
	/**Method lists all commands that user can call.
	 * @return all available commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**Returns MULTILINE symbol.
	 * @return multiline symbol.
	 */
	Character getMultilineSymbol();
	
	/**Sets MULTILINE symbol.
	 * @param new multiline symbol.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**Returns PROMPT symbol.
	 * @return prompt symbol.
	 */
	Character getPromptSymbol();
	
	/**Sets PROMPT symbol.
	 * @param new prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**Returns MORELINES symbol.
	 * @param morelines symbol.
	 */
	Character getMorelinesSymbol();
	
	/**Sets MORELINES symbol.
	 * @param new morelines symbol.
	 */
	void setMorelinesSymbol(Character symbol);
	
}

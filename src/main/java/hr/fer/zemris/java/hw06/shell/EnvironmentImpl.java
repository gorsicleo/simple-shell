package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**Concrete implementation of my environment. 
 * It supports commands: cat, copy, charsets, exit, help, hexdump, ls, mkdir, symbol, tree 
 * For reading and writing standard IO is used. (console output)
 * @author gorsicleo
 *
 */
public class EnvironmentImpl implements Environment {
	
	private char multiLineSymbol = '|';
	private char promptSymbol = '>';
	private char morelinesSymbol = '\\';
	private Scanner sc = new Scanner(System.in);
	
	/**All commands supported by this environment*/
	private SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
	
	/**Creates new environment object with supported methods*/
	public EnvironmentImpl() {
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("exit", new ExitCommand());
		commands.put("help", new HelpCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("symbol", new SymbolCommand());
		commands.put("tree", new TreeCommand());
		
	}


	/**Reads one line from user. It is possible for user to input one line using more than one 
	 * lines using MORELINES symbol, but return of this method will allways be one line since this method concatenates 
	 * multiple lines into one.
	 * @return one line from user input
	 */
	@Override
	public String readLine() throws ShellIOException {
		write(promptSymbol+" ");
		if (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			while (nextLine.endsWith(String.valueOf(morelinesSymbol))) {
				write(morelinesSymbol+" ");
				nextLine = nextLine.substring(0, nextLine.lastIndexOf(morelinesSymbol));
				nextLine += sc.nextLine();
				
			}
			return nextLine;
		} else {
			sc.close();
			throw new ShellIOException("Unable to read next line!");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);

	}

	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);

	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}

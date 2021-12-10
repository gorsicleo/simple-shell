package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

public class EnvironmentImpl implements Environment {
	
	private char multiLineSymbol = '|';
	private char promptSymbol = '>';
	private char morelinesSymbol = '\\';
	private Scanner sc = new Scanner(System.in);
	
	private SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();
	
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

package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;

public class EnvironmentImpl implements Environment {
	
	private char multiLineSymbol = '|';
	private char promptSymbol = '>';
	private char morelinesSymbol = '\\';
	private Scanner sc = new Scanner(System.in);

	@Override
	public String readLine() throws ShellIOException {
		write(promptSymbol+" ");
		if (sc.hasNextLine()) {
			String nextLine = sc.nextLine();
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
		// TODO Auto-generated method stub
		return null;
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

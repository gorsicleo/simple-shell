package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellParser;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellParser.ParsingMode;
import hr.fer.zemris.java.hw06.shell.ShellParser.Token;

public class SymbolCommand implements ShellCommand {
	
	private static final String COMMAND_NAME = "symbol";
	private static final List<String> COMMAND_DESCRIPTION = List.of(
			"Command for changing symbols", 
			"symbol [symbol-type] [new-symbol] ", 
			"\r\r symbol-type - Type of symbol to be changed [PROMPT | MORELINES | MULTILINE]",
			"\r\r new-symbol - New symbol [# | % | $ | ...]");
	
	private List<Token> tokens;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		tokens = new ShellParser(arguments, ParsingMode.ARGUMENTS_PARSING).getTokens();
		
		if (tokens.size() == 1) {
			
			handleSymbolPrint(env);
		}
		
		else if (tokens.size() == 2) {
			String newSymbol = tokens.get(1).getValue();
			if (newSymbol.length() != 1) {
				env.writeln("Argument length not valid: should be character, but was String ("+newSymbol.length()+")");
				return ShellStatus.CONTINUE;
			}
			
			handleSymbolChange(env, newSymbol);
		} else {
			env.writeln("Invalid number of arguments for method symbol should be max 2, but was: "+tokens.size());
		}
		return ShellStatus.CONTINUE;
		
	}

	private void handleSymbolChange(Environment env, String newSymbol) {
		switch (tokens.get(0).getValue()) {
		case "PROMPT":
			env.writeln("Symbol PROMPT changed from '"+env.getPromptSymbol()+"' to '"+newSymbol+"'");
			env.setPromptSymbol(newSymbol.charAt(0));
			break;
		
		case "MORELINES":
			env.writeln("Symbol MORELINES changed from '"+env.getMorelinesSymbol()+"' to '"+newSymbol+"'");
			env.setMorelinesSymbol(newSymbol.charAt(0));
			break;
			
		case "MULTILINE":
			env.writeln("Symbol MULTILINE changed from '"+env.getMultilineSymbol()+"' to '"+newSymbol+"'");
			env.setMultilineSymbol(newSymbol.charAt(0));
			break;

		default:
			env.writeln("Please choose one of the following symbol names: [PROMPT | MORELINES | MULTILINE]");
			break;
		}
	}

	private void handleSymbolPrint(Environment env) {
		switch (tokens.get(0).getValue()) {
		case "PROMPT":
			env.writeln("Symbol for PROMPT is '"+env.getPromptSymbol()+"'");
			break;
		
		case "MORELINES":
			env.writeln("Symbol for MORELINES is '"+env.getMorelinesSymbol()+"'");
			break;
			
		case "MULTILINE":
			env.writeln("Symbol for MULTILINE is '"+env.getMultilineSymbol()+"'");
			break;

		default:
			env.writeln("Please choose one of the following symbol names: [PROMPT | MORELINES | MULTILINE]");
			break;
		}
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return COMMAND_DESCRIPTION;
	}

}
